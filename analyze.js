var express= require("express");
var mysql=require("mysql");
var bodyParser = require('body-parser');
var app = express();

var connection = mysql.createConnection({
    host: 'localhost',
    user:'root',
    database: 'biz',
    password: '1234',
    port:3306,
    multipleStatements: true //다중 쿼리
});

//상권분석 시, 소분류 별 점수 구하기

//전역변수인 각 업종별 밀집도에서의 중위값 median
//21개의 density=lowercnt/population의 중위값
var population;
var median;
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
function ageFloat(){
    //1:35
    //2:~
    //3:65
    var score;
    return score;
}
//4. 연령대 상주인구 점수 구하는 함수
function ageLiving(){
    //1:35
    //2:~
    //3:65
    var score;
    return score;
}
//5. 시간대 유동인구 점수 구하는 함수
function timeFloat(){
    //겹칠 가능성 있으면 인구수 합*0.6
    //1:35
    //2:~
    //3:65
    var score;
    return score;
}
//6. 성별 유동인구 점수 구하는 함수
function genderFloat(gender){
    var score;
    //0.4이하:1, 0.4~0.5:2, 그 이상:3
    return score;
}



var lowerCtg;

switch(lowerCtg){
    case "커피전문점/카페/다방":
        var total=
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
