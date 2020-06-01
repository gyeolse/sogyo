var express= require("express");
var mysql=require("mysql");
var bodyParser = require('body-parser');
var app = express();

var connection = mysql.createConnection({
    host: 'mydbinstance.csygxgspjzz1.us-east-2.rds.amazonaws.com',
    user:'root',
    database: 'biz',
    password: '12341234',
    port:3306
    //multipleStatements: true //다중 쿼리
});

connection.connect();

var pop=[];
var cntbylow=[];


var sql1="select t1.population, t2.lowerCategory,count(*) as cnt from bizZone as t1 join store as t2 on t1.localNo=t2.bizZone_localNo group by t2.lowerCategory";
connection.query(sql1,function(err,rows,fields){
    if(err){
        console.log(err);
    }else{
        var count=0;
        for(var i=0;i<rows.length;i++){
            pop[i]=rows[i].population;
            cntbylow[i]=rows[i].cnt;
            count++;
        }
        console.log(count);
        get_median(pop,cntbylow);
    }
})
//상권분석 시, 소분류 별 점수 구하기

//전역변수인 각 업종별 밀집도에서의 중위값 median
//21개의 density=lowercnt/population의 중위값
var median;
function get_median(pop,cntbylow){
    var dens=[];
    for(var i=0;i<cntbylow.length;i++){
        dens[i]=cntbylow[i]/pop[i];
    }
    dens.sort();
    median=getMedian(dens);
    console.log(median);
}
function getMedian(array) {
    if (array.length == 0) return NaN; // 빈 배열은 에러 반환(NaN은 숫자가 아니라는 의미임)
    var center = parseInt(array.length / 2); // 요소 개수의 절반값 구하기
  
    if (array.length % 2 == 1) { // 요소 개수가 홀수면
      return array[center]; // 홀수 개수인 배열에서는 중간 요소를 그대로 반환
    } else {
      return (array[center - 1] + array[center]) / 2.0; // 짝수 개 요소는, 중간 두 수의 평균 반환
    }
  }
  

//1. 매출추이 점수 구하는 함수
function salesprogress(pre,now){
    //pre: 직전 분기, now: 해당 분기
    var variation=(now-pre)/now;
    //variation(증감률에 따라 점수 부여)
    var score;
    if(variation<-0.2){
        score=1;
    }
    else if(variation>=-0.2&&variation<0){
        score=2;
    }
    else{
        score=3;
        //증갑률이 0%이상 높은편
    }
    return score;
}
//2. 경쟁업체 점수 구하는 함수
function rivals(lowercnt){
    //lowercnt: 소분류로 분류된 업체 수
    //population: 상권의 인구 수
    var density=lowercnt/population;
    //전역변수 median
    var x=density-median;
    var score; 
    if(x>0.2){
        score=1;
        //경쟁업체 다수
    }
    else if(x>=0&&x<=0.2){
        score=2;
    }
    else{
        score=3; //경쟁업체 적음
    }
    return score;
}
//3. 연령대 유동인구 점수 구하는 함수
function ageFloat(needs){
    var ratio=needs/population;
    var score;
    if(ratio<0.35){
        score=1;
    }
    else if(ratio>=0.35&&ratio<0.65){
        score=2;
    }
    else{
        score=3;
    }
    return score;
}
//4. 연령대 상주인구 점수 구하는 함수
function ageLiving(needs){
    var ratio=needs/population;
    if(ratio<0.35){
        score=1;
    }
    else if(ratio>=0.35&&ratio<0.65){
        score=2;
    }
    else{
        score=3;
    }
    //1:35
    //2:~
    //3:65
    var score;
    return score;
}
//5. 시간대 유동인구 점수 구하는 함수
function timeFloat(needs){
    //점심 저녁 야식 시간대 중 겹치는 시간 2개 이상->인구수 합*0.6
    //을 어떻게 짜야할가요?
    var ratio=needs/population;
    if(ratio<0.35){
        score=1;
    }
    else if(ratio>=0.35&&ratio<0.65){
        score=2;
    }
    else{
        score=3;
    }
    var score;
    return score;
}
//6. 성별 유동인구 점수 구하는 함수
function genderFloat(needs){
    var ratio=needs/population;
    if(ratio<0.4){
        score=1;
    }
    else if(ratio>=0.4&&ratio<0.5){
        score=2;
    }
    else{
        score=3;
    }
    var score;
    //0.4이하:1, 0.4~0.5:2, 그 이상:3
    return score;
}



var lowerCtg;

switch(lowerCtg){
    case "커피전문점/카페/다방":
        break;
    case "패스트푸드":
        break;
    case "한식/백반/한정식":
        break;
    case "국수/만두/칼국수":
        break;   
    case "후라이드/양념치킨":
        break;
    case "곱창/양구이전문":
        break;
    case "라면김밥분식":
        break;
    case "중국음식/중국집":
        break; 
    case "동남아음식":
        break;
    case "제과점":
        break;
    case "도시락전문":
        break;
    case "양식":
        break;
    case "유흥주점":
        break;
    case "피자전문":
        break;
    case "죽전문점":
        break;
    case "일식/수산물":
        break;
    case "족발/보쌈전문":
        break;
    case "아이스크림판매":
        break;
    case "떡볶이전문":
        break;
    case "갈비/삼겹살":
        break;
    case "닭/오리요리":
        break;
}