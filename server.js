var mysql = require('mysql');
var express = require('express');
var bodyParser = require('body-parser');
const { response } = require('express');
var app = express();
//var config = require('./config.json');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

app.listen(3000,'localhost',function(){
        console.log('server is running....1');
});

var connection = mysql.createConnection({
        host: 'mydbinstance.csygxgspjzz1.us-east-2.rds.amazonaws.com',
        user:'root',
        database: 'biz',
        password: '12341234',
        port:3306
});

//전역변수
var total_floating;
var floating_10;
var floating_20;
var floating_30;
var floating_40;
var floating_50;


var total_living;
var living_10;
var living_20;
var living_30;
var living_40;
var living_50;

// var day_floating;
// var evening_floating;
// var night_floating;

var male_floating;
var female_floating;

var total_population;


var median;
var day;
var evening;
var night;
var category_score = new Array();
connection.connect();

// get_median();
// var total_score=score_density("유흥주점")*0.3+score_sales("유흥주점")*0.3+score_floating(floating_20+floating_30+floating_40+floating_50)*0.1+score_living(living_20+living_30+living_40+living_50)*0.1+score_time(evening+night,2)*0.2;
// console.log(total_score);
var avg = 0;
bizZone_score();
app.use('/CommercialAnalyze_main',function(req,res){
  
    res.json(avg);
    
});

async function bizZone_score() {
   await get_popluation();
    await get_median();
    var score_arr=await callCategory();
    setTimeout(getavg,20000,score_arr);
    //console.log(avg);
}

// bizZone_score();

function getavg(category_score) {
        var sum=0;
        for (var i = 0; i < category_score.length; i++) {
            sum+=category_score[i];
        }
        console.log("------------");
        //console.log(sum);
        avg = sum / 21;
        console.log(avg);
        
        
}


function callCategory() {
    
    return new Promise(function(resolve){
        soju();
        cafe();
        fastfood();
        korean();
        noodle();
        chicken();
        gobchang();
        ramen();
        china();
        thai();
        bakery();
        dosirak();
        west();
        pizza();
        waterrice();
        japan();
        jokbal();
        icecream();
        ddeok();
        galbi();
        duck();
        resolve(category_score)
    });
}

async function soju() {

    const s1 = await score_density("유흥주점") * 0.3;
    const s2 = await score_sales("유흥주점") * 0.3;
    const s3 = await score_floating(floating_20 + floating_30 + floating_40 + floating_50) * 0.1;
    const s4 = await score_living(living_20 + living_30 + living_40 + living_50) * 0.1;
    const s5 = await score_time(evening + night, 2) * 0.2;
    var total = s1 + s2 + s3 + s4 + s5;
    //console.log(s1+","+s2+","+s3+","+s4+","+s5);
       // console.log(total);
        category_score[12] = total;
   
    //console.log(total);
}

async function cafe() {

    const s1 = await score_density("커피전문점/카페/다방") * 0.25;
    const s2 = await score_sales("커피전문점/카페/다방") * 0.25;
    const s3 = await score_floating(floating_20 + floating_30) * 0.15;
    const s4 = await score_living(living_20 + living_30) * 0.15;
    const s5 = await score_time(day + evening, 2) * 0.15;
    const s6 = await score_gender(female_floating) * 0.05;
    var total = s1 + s2 + s3 + s4 + s5 + s6;
    category_score[0] = total;
    //console.log(s1+","+s2+","+s3+","+s4+","+s5);
    //console.log(total);
    //console.log(total);
}

async function fastfood() {

    const s1 = await score_density("패스트푸드") * 0.25;
    const s2 = await score_sales("패스트푸드") * 0.25;
    const s3 = await score_floating(floating_20 + floating_10) * 0.2;
    const s4 = await score_living(living_20 + living_10) * 0.1;
    const s5 = await score_time(day, 1) * 0.2;
    var total = s1 + s2 + s3 + s4 + s5;
    // console.log(s1+","+s2+","+s3+","+s4+","+s5);
    category_score[1] = total;
    //console.log(total);
    //console.log(total);
}

async function korean() {

    const s1 = await score_density("한식/백반/한정식") * 0.25;
    const s2 = await score_sales("한식/백반/한정식") * 0.35;
    const s3 = await score_floating(floating_10 + floating_20 + floating_30 + floating_40 + floating_50) * 0.15;
    const s4 = await score_living(living_10 + living_20 + living_30 + living_40 + living_50) * 0.15;
    const s5 = await score_time(evening + night, 2) * 0.15;
    var total = s1 + s2 + s3 + s4 + s5;
    //console.log(s1+","+s2+","+s3+","+s4+","+s5);
    category_score[2] = total;
    //console.log(total);
    //console.log(total);
}

async function noodle() {

    const s1 = await score_density("국수/만두/칼국수") * 0.2;
    const s2 = await score_sales("국수/만두/칼국수") * 0.2;
    const s3 = await score_floating(floating_10 + floating_20 + floating_30 + floating_40 + floating_50) * 0.2;
    const s4 = await score_living(living_10 + living_20 + living_30 + living_40 + living_50) * 0.2;
    const s5 = await score_time(day, 1) * 0.2;
    var total = s1 + s2 + s3 + s4 + s5;
    //console.log(s1+","+s2+","+s3+","+s4+","+s5);
    //console.log(total);
    category_score[3] = total;
    //console.log(total);
}

async function chicken() {

    const s1 = await score_density("후라이드/양념치킨") * 0.25;
    const s2 = await score_sales("후라이드/양념치킨") * 0.25;
    const s3 = await score_floating(floating_20 + floating_10) * 0.1;
    const s4 = await score_living(living_20 + living_10) * 0.2;
    const s5 = await score_time(evening + night, 2) * 0.2;
    var total = s1 + s2 + s3 + s4 + s5;
    //console.log(s1+","+s2+","+s3+","+s4+","+s5);
    //console.log(total);
    category_score[4] = total;
    //console.log(total);
}

async function gobchang() {

    const s1 = await score_density("곱창/양구이전문") * 0.2;
    const s2 = await score_sales("곱창/양구이전문") * 0.2;
    const s3 = await score_floating(floating_20 + floating_30 + floating_40) * 0.2;
    const s4 = await score_living(living_20 + living_30 + living_40) * 0.2;
    const s5 = await score_time(evening + night, 2) * 0.2;
    var total = s1 + s2 + s3 + s4 + s5;
    //console.log(s1+","+s2+","+s3+","+s4+","+s5);
    //console.log(total);
    category_score[5] = total;
    //console.log(total);
}

async function ramen() {

    const s1 = await score_density("라면김밥분식") * 0.3;
    const s2 = await score_sales("라면김밥분식") * 0.25;
    const s3 = await score_floating(floating_20 + floating_10) * 0.1;
    const s4 = await score_living(living_20 + living_10) * 0.25;
    const s5 = await score_time(day, 1) * 0.1;
    var total = s1 + s2 + s3 + s4 + s5;
    //console.log(s1+","+s2+","+s3+","+s4+","+s5);
    //console.log(total);
    category_score[6] = total;
    //console.log(total);
}

async function china() {

    const s1 = await score_density("중국음식/중국집") * 0.25;
    const s2 = await score_sales("중국음식/중국집") * 0.25;
    const s3 = await score_floating(floating_10 + floating_20 + floating_30 + floating_40 + floating_50) * 0.2;
    const s4 = await score_living(living_10 + living_20 + living_30 + living_40 + living_50) * 0.2;
    const s5 = await score_time(evening + day, 2) * 0.1;
    var total = s1 + s2 + s3 + s4 + s5;
    //console.log(s1+","+s2+","+s3+","+s4+","+s5);
    //console.log(total);
    category_score[7] = total;
    //console.log(total);
}

async function thai() {

    const s1 = await score_density("동남아음식") * 0.2;
    const s2 = await score_sales("동남아음식") * 0.25;
    const s3 = await score_floating(floating_20 + floating_30) * 0.2;
    const s4 = await score_living(living_20 + living_30) * 0.1;
    const s5 = await score_time(day, 1) * 0.2;
    const s6 = await score_gender(female_floating) * 0.05;
    var total = s1 + s2 + s3 + s4 + s5 + s6;
    //console.log(s1+","+s2+","+s3+","+s4+","+s5);
   // console.log(total);
    category_score[8] = total;
    //console.log(total);
}

async function bakery() {

    const s1 = await score_density("제과점") * 0.25;
    const s2 = await score_sales("제과점") * 0.2;
    const s3 = await score_floating(floating_10 + floating_20 + floating_30 + floating_40) * 0.25;
    const s4 = await score_living(floating_10 + living_20 + living_30 + living_40) * 0.15;
    const s5 = await score_time(evening + day, 2) * 0.1;
    const s6 = await score_gender(female_floating) * 0.05;

    var total = s1 + s2 + s3 + s4 + s5 + s6;
    category_score[9] = total;
    //console.log(s1+","+s2+","+s3+","+s4+","+s5);
   // console.log(total);
    //console.log(total);
}

async function dosirak() {

    const s1 = await score_density("도시락전문점") * 0.25;
    const s2 = await score_sales("도시락전문점") * 0.25;
    const s3 = await score_floating(floating_20 + floating_10) * 0.25;
    const s4 = await score_living(living_20 + living_10) * 0.15;
    const s5 = await score_time(evening + day, 2) * 0.1;
    var total = s1 + s2 + s3 + s4 + s5;
    //console.log(s1+","+s2+","+s3+","+s4+","+s5);
    //console.log(total);
    category_score[10] = total;
    //console.log(total);
}

async function west() {

    const s1 = await score_density("양식") * 0.25;
    const s2 = await score_sales("양식") * 0.25;
    const s3 = await score_floating(floating_20 + floating_30) * 0.25;
    const s4 = await score_living(living_20 + living_30) * 0.05;
    const s5 = await score_time(evening + day, 2) * 0.15;
    const s6 = await score_gender(female_floating) * 0.05;

    var total = s1 + s2 + s3 + s4 + s5 + s6;
    //console.log(s1+","+s2+","+s3+","+s4+","+s5);
   // console.log(total);
    category_score[11] = total;
    //console.log(total);
}

async function pizza() {

    const s1 = await score_density("피자전문") * 0.25;
    const s2 = await score_sales("피자전문") * 0.25;
    const s3 = await score_floating(floating_20 + floating_10) * 0.05;
    const s4 = await score_living(living_20 + living_10) * 0.25;
    const s5 = await score_time(evening, 1) * 0.2;
    var total = s1 + s2 + s3 + s4 + s5;
    //console.log(s1+","+s2+","+s3+","+s4+","+s5);
    //console.log(total);
    category_score[13] = total;
    //console.log(total);
}

async function waterrice() {

    const s1 = await score_density("죽전문점") * 0.3;
    const s2 = await score_sales("죽전문점") * 0.25;
    const s3 = await score_floating(floating_10 + floating_20 + floating_30 + floating_40 + floating_50) * 0.1;
    const s4 = await score_living(living_10 + living_20 + living_30 + living_40 + living_50) * 0.25;
    const s5 = await score_time(day, 1) * 0.1;
    var total = s1 + s2 + s3 + s4 + s5;
    //console.log(s1+","+s2+","+s3+","+s4+","+s5);
   // console.log(total);
    category_score[14] = total;
    //console.log(total);
}

async function japan() {

    const s1 = await score_density("일식/수산물") * 0.2;
    const s2 = await score_sales("일식/수산물") * 0.2;
    const s3 = await score_floating(floating_10 + floating_20 + floating_30 + floating_40 + floating_50) * 0.2;
    const s4 = await score_living(living_10 + living_20 + living_30 + living_40 + living_50) * 0.2;
    const s5 = await score_time(day + evening + night, 3) * 0.2;
    var total = s1 + s2 + s3 + s4 + s5;
    //console.log(s1+","+s2+","+s3+","+s4+","+s5);
    //console.log(total);
    category_score[15] = total;
    //console.log(total);
}

async function jokbal() {

    const s1 = await score_density("족발/보쌈전문") * 0.2;
    const s2 = await score_sales("족발/보쌈전문") * 0.25;
    const s3 = await score_floating(floating_20 + floating_30 + floating_40 + floating_50) * 0.1;
    const s4 = await score_living(living_20 + living_30 + living_40 + living_50) * 0.25;
    const s5 = await score_time(evening + night, 2) * 0.2;
    var total = s1 + s2 + s3 + s4 + s5;
    //console.log(s1+","+s2+","+s3+","+s4+","+s5);
    //console.log(total);
    category_score[16] = total;
    //console.log(total);
}

async function icecream() {

    const s1 = await score_density("아이스크림판매") * 0.25;
    const s2 = await score_sales("아이스크림판매") * 0.25;
    const s3 = await score_floating(floating_20 + floating_10) * 0.2;
    const s4 = await score_living(living_20 + living_10) * 0.15;
    const s5 = await score_time(evening + day, 2) * 0.1;
    const s6 = await score_gender(female_floating) * 0.05;
    var total = s1 + s2 + s3 + s4 + s5 + s6;
    //console.log(s1+","+s2+","+s3+","+s4+","+s5);
   // console.log(total);
    category_score[17] = total;
    //console.log(total);
}

async function ddeok() {

    const s1 = await score_density("떡볶이전문") * 0.25;
    const s2 = await score_sales("떡볶이전문") * 0.2;
    const s3 = await score_floating(floating_20 + floating_10) * 0.2;
    const s4 = await score_living(living_20 + living_10) * 0.2;
    const s5 = await score_time(evening + day, 2) * 0.05;
    const s6 = await score_gender(female_floating) * 0.1;
    var total = s1 + s2 + s3 + s4 + s5 + s6;
    //console.log(s1+","+s2+","+s3+","+s4+","+s5);
    //console.log(total);
    category_score[18] = total;
    //console.log(total);
}

async function galbi() {

    const s1 = await score_density("갈비/삼겹살") * 0.25;
    const s2 = await score_sales("갈비/삼겹살") * 0.25;
    const s3 = await score_floating(floating_20 + floating_30 + floating_40 + floating_50) * 0.2;
    const s4 = await score_living(living_20 + living_30 + living_40 + living_50) * 0.1;
    const s5 = await score_time(evening, 1) * 0.2;
    var total = s1 + s2 + s3 + s4 + s5;
    //console.log(s1+","+s2+","+s3+","+s4+","+s5);
    //console.log(total);
    category_score[19] = total;
    //console.log(total);
}

async function duck() {

    const s1 = await score_density("닭/오리요리") * 0.3;
    const s2 = await score_sales("닭/오리요리") * 0.3;
    const s3 = await score_floating(floating_20 + floating_30 + floating_40 + floating_50) * 0.15;
    const s4 = await score_living(living_20 + living_30 + living_40 + living_50) * 0.15;
    const s5 = await score_time(evening + day, 2) * 0.1;
    var total = s1 + s2 + s3 + s4 + s5;
    //console.log(s1+","+s2+","+s3+","+s4+","+s5);
    //console.log(total);
    category_score[20] = total;
    //console.log(total);
}


/*
//쿼리 양식
var sql="";
connection.query(sql,function(err,rows,fields){
    if(err){
        console.log(err);
    }
    else{

    }
})
*/


//전체 유동인구와 상주인구를 구하는 함수
function get_popluation() {
    return new Promise(function (resolve, reject) {
        var sql3 = "select age,sum(population) as FP from Floating_people group by age";
        var sql4 = "select age,sum(population) as LP from Living_people group by age";
        var sql_time = "select time, sum(population) as TP from Floating_people group by time";
       var gender_sql="select gender,sum(population) as GP from Floating_people group by gender";
        connection.query(sql_time, function (err, rows, fields) {
            if (err) {
                console.log(err);
                reject(err);
            }
            else {
                var t = new Array();
                for (var i = 0; i < 24; i++) {
                    t[i] = rows[i].TP;
                }
                day = t[11] + t[12] + t[13] + t[14] + t[15];
                evening = t[17] + t[18] + t[19] + t[20];
                night = t[21] + t[22] + t[23] + t[0] + t[1] + t[2] + t[3];
            }

        })
        connection.query(sql3, function (err, row3, fields) {
            if (err) {
                console.log(err);
            }
            else {
                floating_10 = row3[0].FP;
                floating_20 = row3[1].FP;
                floating_30 = row3[2].FP;
                floating_40 = row3[3].FP;
                floating_50 = row3[4].FP;
                total_floating = floating_10 + floating_20 + floating_30 + floating_40 + floating_50;
            }
        })
        connection.query(sql4, function (err, rows4, fields) {
            if (err) {
                console.log(err);
            }
            else {
                living_10 = rows4[0].LP;
                living_20 = rows4[1].LP;
                living_30 = rows4[2].LP;
                living_40 = rows4[3].LP;
                living_50 = rows4[4].LP;
                total_living = living_10 + living_20 + living_30 + living_40 + living_50;
            }
        })
        connection.query(gender_sql,function(err,g_rows,fields){
            if(err){
                console.log(err);
            }
            else{
                female_floating=g_rows[0].GP;
                male_floating=g_rows[1].GP;
            }
        })
        resolve(0);
    })
}

//중위값 구하기 필요쿼리 = 총인구수를 불러오고 소분류의 가게수 알아내는 쿼리
function get_median() {
    return new Promise(function (resolve, reject) {
        var dens = [];
        var sql1 = "select t1.population, t2.lowerCategory,count(*) as cnt from bizZone as t1 join store as t2 on t1.localNo=t2.bizZone_localNo group by t2.lowerCategory";
        connection.query(sql1, function (err, rows, fields) {
            if (err) {
                console.log(err);
            } else {
                total_population = rows[0].population;
                var cntbylow = new Array();
                var dens = new Array();
                for (var i = 0; i < rows.length; i++) {
                    cntbylow[i] = rows[i].cnt;

                }
                for (var i = 0; i < cntbylow.length; i++) {
                    dens[i] = cntbylow[i] / total_population;
                }
                dens.sort();
                var center = parseInt(dens.length / 2); // 요소 개수의 절반값 구하기
                if (dens.length % 2 == 1) { // 요소 개수가 홀수면
                    median = dens[center]; // 홀수 개수인 배열에서는 중간 요소를 그 대로 반환
                } else {
                    median = (dens[center - 1] + dens[center]) / 2.0; // 짝수 개 요소는, 중간 두 수의 평균 반
                }
                //    console.log(median);
                // for(var i =0;i<cntbylow.length;i++){
                //  console.log(dens[i]-median);
                //}
            }
        })
        resolve(0);
    })
}

//밀집도에 따른 점수
function score_density(lowerCategory) {
    //lowercnt: 소분류로 분류된 업체 수
    //population: 상권의 인구 수
    return new Promise(function (resolve, reject) {
        var sql = "select count(*) as cnt from bizZone as t1 join store as t2 on t1.localNo=t2.bizZone_localNo where lowerCategory='" + lowerCategory + "' group by t2.lowerCategory";
        connection.query(sql, function (err, rows, fields) {
            if (err) {
                console.log(err);
                reject(err);
            }
            else {
                var store_cnt = rows[0].cnt;
                var density = store_cnt / total_population;
                var x = density - median;
                var score;
                if (x > 0.002) {
                    score = 1;
                    //경쟁업체 다수
                }
                else if (x >= -0.005 && x <= 0.2) {
                    score = 2;
                }
                else {
                    score = 3; //경쟁업체 적음
                }
                //console.log(score);
                resolve(score)
            }
        })
    });

}



//매출 추이에 따른 점수
// function score_sales(lowerCategory) {
//     //pre: 직전 분기, now: 해당 분기
//     var sql="select quarter,qt_sales from sales where lowerCategory='"+lowerCategory+"' and (quarter=3 or quarter=4)";
//     connection.query(sql,function(err,rows,fields){
//         if(err){
//             console.log(err);
//         }
//         else{
//             var qt_sales_prev=rows[0].qt_sales;
//             var qt_sales_current=rows[1].qt_sales;

//             var variation = (qt_sales_current - qt_sales_prev) / qt_sales_current;
//             var score;
//             if (variation < -0.2) {
//                 score = 1;
//             }
//             else if (variation >= -0.2 && variation < 0) {
//                 score = 2;
//             }
//             else {
//                 score = 3;
//                 //증갑률이 0%이상 높은편
//             }
//             console.log(score);
//             return score;
//                 }
//     })
// }

function score_sales(lowerCategory) {
    //pre: 직전 분기, now: 해당 분기
    return new Promise(function (resolve, reject) {
        var sql = "select quarter,qt_sales from sales where lowerCategory='" + lowerCategory + "' and (quarter=3 or quarter=4)";
        var score;
        connection.query(sql, function (err, rows, fields) {
            if (err) {
                console.log(err);
                reject(err);
            }
            else {
                var qt_sales_prev = rows[0].qt_sales;
                var qt_sales_current = rows[1].qt_sales;

                var variation = (qt_sales_current - qt_sales_prev) / qt_sales_current;
                if (variation < -0.2) {
                    score = 1;
                }
                else if (variation >= -0.2 && variation < 0) {
                    score = 2;
                }
                else {
                    score = 3;
                    //증갑률이 0%이상 높은편
                }
                // console.log(score);
                resolve(score)
            }

        })

    });
}

//시간대 유동인구에 따른 점수
function score_time(time_people, length) {
    if (length >= 2) {
        time_people *= 0.6;
    }
    rate = time_people / total_floating;
    if (rate < 0.35) {
        score = 1;
    }
    else if (rate >= 0.35 && rate < 0.65) {
        score = 2;
    }
    else {
        score = 3;
        //증갑률이 0%이상 높은편
    }
    return score;
}

//연령대 유동인구에 따른 점수 부여
function score_floating(age_floating_people) {

    rate = age_floating_people / total_floating;
    if (rate < 0.35) {
        score = 1;
    }
    else if (rate >= 0.35 && rate < 0.65) {
        score = 2;
    }
    else {
        score = 3;
        //증갑률이 0%이상 높은편
    }
    return score;
}

//연령대 상주인구에 따른 점수 부여
function score_living(age_living_people) {
    rate = age_living_people / total_living;
    if (rate < 0.35) {
        score = 1;
    }
    else if (rate >= 0.35 && rate < 0.65) {
        score = 2;
    }
    else {
        score = 3;
        //증갑률이 0%이상 높은편
    }
    return score;
}

//성별에 따른 점수부여
function score_gender(gender_population) {
    return new Promise(function (resolve, reject) {
        rate = gender_population / total_floating;
        if (rate < 0.4) {
            score = 1;
        }
        else if (rate >= 0.4 && rate < 0.5) {
            score = 2;
        }
        else {
            score = 3;
            //증갑률이 0%이상 높은편
        }
        resolve(score);
    })
}
//test




//?  권분?   그래?   분기 ? 매출?
app.get('/CommercialAnalyze',function(req,res){
        var sql="select quarter,lowerCategory, qt_sales from sales;";
        connection.query(sql,function(err,result){
                if(err){
                        console.log(err);
                }
                else{
                        console.log(result);
                        res.json(result);
                        console.log('success!!!!!');
                }
        })
});



var ctgpath=['cafe','fastfood','korean','noodle','chicken','gobchang','ramen','china','thai','bakery','dosirak','west','drink','pizza','waterrice','japan','jokbal','icecream','ddeok','galbi','duck'];
var ctglist=['커피전문점/카페/다방','패스트푸드','한식/백반/한정식','국수/만두/칼국수','후라이드/양념치킨','곱창/양구이전문','라면김밥분식','중국음식/중국집','동남아음식','제과점','도시락전문점','양식','유흥주점','피자전문','죽전문점','일식/수산물','족발/보쌈전문','아이스크림판매','떡볶이전문','갈비/삼겹살','닭/오리요리'];

app.get('/CommercialAnalyze/cafe', function (req, res) {
        var sql = "select qt_sales, quarter from sales where lowerCategory='커피전문점/카페/다방'";
        connection.query(sql, function (err, result) {
                if (err) {
                        console.log(err);
                }
                else {
                        res.json(result);
                        console.log("success!");
                }
        })
});

app.get('/CommercialAnalyze/fastfood', function (req, res) {
        var sql = "select qt_sales, quarter from sales where lowerCategory='패스트푸드'";
        connection.query(sql, function (err, result) {
                if (err) {
                        console.log(err);
                }
                else {
                        res.json(result);
                        console.log("success!");
                }
        })
});

app.get('/CommercialAnalyze/korean', function (req, res) {
        var sql = "select qt_sales, quarter from sales where lowerCategory='한식/백반/한정식'";
        connection.query(sql, function (err, result) {
                if (err) {
                        console.log(err);
                }
                else {
                        res.json(result);
                        console.log("success!");
                }
        })
});

app.get('/CommercialAnalyze/noodle' , function (req, res) {
        var sql = "select qt_sales, quarter from sales where lowerCategory='국수/만두/칼국수'";
        connection.query(sql, function (err, result) {
                if (err) {
                        console.log(err);
                }
                else {
                        res.json(result);
                        console.log("success!");
                }
        })
});

app.get('/CommercialAnalyze/chicken' , function (req, res) {
        var sql = "select qt_sales, quarter from sales where lowerCategory='후라이드/양념치킨'";
        connection.query(sql, function (err, result) {
                if (err) {
                        console.log(err);
                }
                else {
                        res.json(result);
                        console.log("success!");
                }
        })
});

app.get('/CommercialAnalyze/gobchang', function (req, res) {
        var sql = "select qt_sales, quarter from sales where lowerCategory='곱창/양구이전문'";
        connection.query(sql, function (err, result) {
                if (err) {
                        console.log(err);
                }
                else {
                        res.json(result);
                        console.log("success!");
                }
        })
});

app.get('/CommercialAnalyze/ramen' , function (req, res) {
        var sql = "select qt_sales, quarter from sales where lowerCategory='라면김밥분식'";
        connection.query(sql, function (err, result) {
                if (err) {
                        console.log(err);
                }
                else {
                        res.json(result);
                        console.log("success!");
                }
        })
});

app.get('/CommercialAnalyze/china', function (req, res) {
        var sql = "select qt_sales, quarter from sales where lowerCategory='중국음식/중국집'";
        connection.query(sql, function (err, result) {
                if (err) {
                        console.log(err);
                }
                else {
                        res.json(result);
                        console.log("success!");
                }
        })
});

app.get('/CommercialAnalyze/thai', function (req, res) {
        var sql = "select qt_sales, quarter from sales where lowerCategory='동남아음식'";
        connection.query(sql, function (err, result) {
                if (err) {
                        console.log(err);
                }
                else {
                        res.json(result);
                        console.log("success!");
                }
        })
});

app.get('/CommercialAnalyze/bakery', function (req, res) {
        var sql = "select qt_sales, quarter from sales where lowerCategory='제과점'";
        connection.query(sql, function (err, result) {
                if (err) {
                        console.log(err);
                }
                else {
                        res.json(result);
                        console.log("success!");
                }
        })
});

app.get('/CommercialAnalyze/dosirak', function (req, res) {
        var sql = "select qt_sales, quarter from sales where lowerCategory='도시락전문점'";
        connection.query(sql, function (err, result) {
                if (err) {
                        console.log(err);
                }
                else {
                        res.json(result);
                        console.log("success!");
                }
        })
});

app.get('/CommercialAnalyze/west' , function (req, res) {
        var sql = "select qt_sales, quarter from sales where lowerCategory='양식'";
        connection.query(sql, function (err, result) {
                if (err) {
                        console.log(err);
                }
                else {
                        res.json(result);
                        console.log("success!");
                }
        })
});

 app.get('/CommercialAnalyze/drink', function (req, res) {
                var sql = "select qt_sales, quarter from sales where lowerCategory='유흥주점'";
                connection.query(sql, function (err, result) {
                        if (err) {
                                console.log(err);
                        }
                        else {
                                res.json(result);
                                console.log("success!");
                        }
                })
        });
        app.get('/CommercialAnalyze/pizza', function (req, res) {
                var sql = "select qt_sales, quarter from sales where lowerCategory='피자전문'";
                connection.query(sql, function (err, result) {
                        if (err) {
                                console.log(err);
                        }
                        else {
                                res.json(result);
                                console.log("success!");
                        }
                })
        });
        app.get('/CommercialAnalyze/waterrice', function (req, res) {
                var sql = "select qt_sales, quarter from sales where lowerCategory='죽전문점'";
                connection.query(sql, function (err, result) {
                        if (err) {
                                console.log(err);
                        }
                        else {
                                res.json(result);
                                console.log("success!");
                        }
                })
        });
        app.get('/CommercialAnalyze/japan', function (req, res) {
                var sql = "select qt_sales, quarter from sales where lowerCategory='일식/수산물'";
                connection.query(sql, function (err, result) {
                        if (err) {
                                console.log(err);
                        }
                        else {
                                res.json(result);
                                console.log("success!");
                        }
                })
        });
        app.get('/CommercialAnalyze/jokbal', function (req, res) {
                var sql = "select qt_sales, quarter from sales where lowerCategory='족발/보쌈전문'";
                connection.query(sql, function (err, result) {
                        if (err) {
                                console.log(err);
                        }
                        else {
                                res.json(result);
                                console.log("success!");
                        }
                })
        });
        app.get('/CommercialAnalyze/icecream', function (req, res) {
                var sql = "select qt_sales, quarter from sales where lowerCategory='아이스크림판매'";
                connection.query(sql, function (err, result) {
                        if (err) {
                                console.log(err);
                        }
                        else {
                                res.json(result);
                                console.log("success!");
                        }
                })
        });
        app.get('/CommercialAnalyze/ddeok' , function (req, res) {
                var sql = "select qt_sales, quarter from sales where lowerCategory='떡볶이전문'";
                connection.query(sql, function (err, result) {
                        if (err) {
                                console.log(err);
                        }
                        else {
                                res.json(result);
                                console.log("success!");
                        }
                })
        });
        app.get('/CommercialAnalyze/galbi', function (req, res) {
                var sql = "select qt_sales, quarter from sales where lowerCategory='갈비/삼겹살'";
                connection.query(sql, function (err, result) {
                        if (err) {
                                console.log(err);
                        }
                        else {
                                res.json(result);
                                console.log("success!");
                        }
                })
        });
        app.get('/CommercialAnalyze/duck', function (req, res) {
                var sql = "select qt_sales, quarter from sales where lowerCategory='닭/오리요리'";
                connection.query(sql, function (err, result) {
                        if (err) {
                                console.log(err);
                        }
                        else {
                                res.json(result);
                                console.log("success!");
                        }
                })
        });


app.get('/history/location',function(req,res){
       // console.log(req.query);
        var sql = 'select * from store';
        connection.query(sql,function(err,result){
                var resultCode = 100;
                var message = 'Error Occured!';

                if(err){
                        console.log(err);
                } else{
                        console.log(result);
                        message = 'success!';
                        console.log('success!!!!!');

                        res.json(result);
                }

        })
});

app.post('/history/location/info',function(req,res){
        console.log(req.body);
        var longitude = req.body[0].longitude;
        var latitude = req.body[0].latitude;
        console.log(longitude,latitude);
// console.log(req.query);
// select * from store where longitude="126.6578959" and latitude = "37.4531151";
//왜 반대로 들어가지...?
        var sql = "select * from store where longitude=? and latitude = ?";
        connection.query(sql,[latitude,longitude],function(err,result){
                 var resultCode = 100;
                 var message = 'Error Occured!'; 
                 if(err){
                         console.log(err);
                 } else{                        
                         message = 'success!';
                         console.log('success!!!!!');
                         console.log(result);
                         res.json(result);
                 }
 
         });
 });


app.get('/CommercialAnalyze/bytime',function(req,res){
        var sql="select time,sum(population) as pop from Floating_people group by time;";
        connection.query(sql,function(err,result){
                if(err){
                        console.log(err);
                }
                else{
                        console.log(result);
                        res.json(result);
                        console.log('success!!!!!');
                }
        })
});

app.get('/CommercialAnalyze/byage',function(req,res){
        var sql="select age,sum(population) as pop from Floating_people group by age;";
        connection.query(sql,function(err,result){
                if(err){
                        console.log(err);
                }
                else{
                        console.log(result);
                        res.json(result);
                        console.log('success!!!!!');
                }
        })
});
app.post('/CommercialAnalyze/total',function(req,res){

var sql_test=req.body[0].Q;
console.log(sql_test);
connection.query(sql_test,function(err,result){
                if(err){
                        console.log(err);
                }
                else{
                        console.log(result);
                        res.json(result);
                        console.log('success!!!!!');
                }
        });
});
app.get('/CommercialAnalyze/bygender',function(req,res){
        var sql="select gender,sum(population) as pop from Floating_people group by gender;";
        connection.query(sql,function(err,result){
                if(err){
                        console.log(err);
                }
                else{
                        console.log(result);
                        res.json(result);
                        console.log('success!!!!!');
                }
        })
});

//추가
var ctg;
var cost;
var judge_score;
app.post('/judgement',async(req,res)=>{ 
        console.log(req.body);
        ctg=req.body.category;
        cost=req.body.cost;
        console.log(ctg);
        // for(var i=0;i<category_score.length;i++){
        //         console.log(category_score[i]);
        // }
       if (ctg=="커피전문점/카페/다방"){
                await preference("커피전문점/카페/다방");
                //하위분류점수+선호도 점수 합산값
                console.log(prefer_score+","+category_score[0]);
                judge_score=prefer_score+category_score[0];
                console.log(judge_score);

                var jsonstr={"judge_score":judge_score};
                console.log(jsonstr);
                res.json(jsonstr);
       }
       else if(ctg=="패스트푸드"){
                await preference("패스트푸드");
                //하위분류점수+선호도 점수 합산값
                 //1~3:0.1~2.1->1.1~5.1
                 console.log(prefer_score);
                 judge_score=prefer_score+category_score[1];
                 console.log(judge_score); 

                 var jsonstr={"judge_score":judge_score};
                 console.log(jsonstr);
                 res.json(jsonstr);
 
       }
       else if(ctg=="한식/백반/한정식"){
        await preference("한식/백반/한정식");
                //하위분류점수+선호도 점수 합산값
                console.log(prefer_score+","+category_score[2]);
                judge_score=prefer_score+category_score[2];
                console.log(judge_score);

                var jsonstr={"judge_score":judge_score};
                console.log(jsonstr);
                res.json(jsonstr);


       }
       else if(ctg=="국수/만두/칼국수"){
        await preference("국수/만두/칼국수");
                //하위분류점수+선호도 점수 합산값
                console.log(prefer_score+","+category_score[3]);
                judge_score=prefer_score+category_score[3];
                console.log(judge_score);

                var jsonstr={"judge_score":judge_score};
                console.log(jsonstr);
                res.json(jsonstr);

        }
        else if(ctg=="후라이드/양념치킨"){
                await preference("후라이드/양념치킨");
                //하위분류점수+선호도 점수 합산값
                console.log(prefer_score+","+category_score[4]);
                judge_score=prefer_score+category_score[4];
                console.log(judge_score);

                var jsonstr={"judge_score":judge_score};
                console.log(jsonstr);
                res.json(jsonstr);

        }
        else if(ctg=="곱창/양구이전문"){

                await preference("곱창/양구이전문");
                //하위분류점수+선호도 점수 합산값
                console.log(prefer_score+","+category_score[5]);
                judge_score=prefer_score+category_score[5];
                console.log(judge_score);

                var jsonstr={"judge_score":judge_score};
                console.log(jsonstr);
                res.json(jsonstr);

        }
        else if(ctg=="라면김밥분식"){
                await preference("라면김밥분식");
                //하위분류점수+선호도 점수 합산값
                console.log(prefer_score+","+category_score[6]);
                judge_score=prefer_score+category_score[6];
                console.log(judge_score);

                var jsonstr={"judge_score":judge_score};
                console.log(jsonstr);
                res.json(jsonstr);

        }
        else if(ctg=="중국음식/중국집"){
              
                await preference("중국음식/중국집");
                //하위분류점수+선호도 점수 합산값
                console.log(prefer_score+","+category_score[7]);
                judge_score=prefer_score+category_score[7];
                console.log(judge_score);

                var jsonstr={"judge_score":judge_score};
                console.log(jsonstr);
                res.json(jsonstr);

        }
        else if(ctg=="동남아음식"){
            
                await preference("동남아음식");
                //하위분류점수+선호도 점수 합산값
                console.log(prefer_score+","+category_score[8]);
                judge_score=prefer_score+category_score[8];
                console.log(judge_score);

                var jsonstr={"judge_score":judge_score};
                console.log(jsonstr);
                res.json(jsonstr);

        }
        else if(ctg=="제과점"){
              
                await preference("제과점");
                //하위분류점수+선호도 점수 합산값
                console.log(prefer_score+","+category_score[9]);
                judge_score=prefer_score+category_score[9];
                console.log(judge_score);

                var jsonstr={"judge_score":judge_score};
                console.log(jsonstr);
                res.json(jsonstr);

        }
        else if(ctg=="도시락전문"){
               
                await preference("도시락전문");
                //하위분류점수+선호도 점수 합산값
                console.log(prefer_score+","+category_score[10]);
                judge_score=prefer_score+category_score[10];
                console.log(judge_score);

                var jsonstr={"judge_score":judge_score};
                console.log(jsonstr);
                res.json(jsonstr);

        }
        else if(ctg=="양식"){
                await  preference("양식");
                //하위분류점수+선호도 점수 합산값
                console.log(prefer_score+","+category_score[11]);
                judge_score=prefer_score+category_score[11];
                console.log(judge_score);

                var jsonstr={"judge_score":judge_score};
                console.log(jsonstr);
                res.json(jsonstr);

        }
        else if(ctg=="유흥주점"){
                await preference("유흥주점");
                //하위분류점수+선호도 점수 합산값
                console.log(prefer_score+","+category_score[12]);
                judge_score=prefer_score+category_score[12];
                console.log(judge_score);

                var jsonstr={"judge_score":judge_score};
                console.log(jsonstr);
                res.json(jsonstr);

        }
        else if(ctg=="피자전문"){
                await preference("피자전문");
                //하위분류점수+선호도 점수 합산값
                console.log(prefer_score+","+category_score[13]);
                judge_score=prefer_score+category_score[13];
                console.log(judge_score);

                var jsonstr={"judge_score":judge_score};
                console.log(jsonstr);
                res.json(jsonstr);

        }
        else if(ctg=="죽전문점"){
                await preference("죽전문점");
                //하위분류점수+선호도 점수 합산값
                console.log(prefer_score+","+category_score[14]);
                judge_score=prefer_score+category_score[14];
                console.log(judge_score);

                var jsonstr={"judge_score":judge_score};
                console.log(jsonstr);
                res.json(jsonstr);

        }
        else if(ctg=="일식/수산물"){
               
                await preference("일식/수산물");
                //하위분류점수+선호도 점수 합산값
                console.log(prefer_score+","+category_score[15]);
                judge_score=prefer_score+category_score[15];
                console.log(judge_score);

                var jsonstr={"judge_score":judge_score};
                console.log(jsonstr);
                res.json(jsonstr);

        }
        else if(ctg=="족발/보쌈전문"){
               
                await preference("족발/보쌈전문");
                //하위분류점수+선호도 점수 합산값
                console.log(prefer_score+","+category_score[16]);
                judge_score=prefer_score+category_score[16];
                console.log(judge_score);
                
                var jsonstr={"judge_score":judge_score};
                console.log(jsonstr);
                res.json(jsonstr);

        }
        else if(ctg=="아이스크림판매"){
              
                await  preference("아이스크림판매");
                //하위분류점수+선호도 점수 합산값
                console.log(prefer_score+","+category_score[17]);
                judge_score=prefer_score+category_score[17];
                console.log(judge_score);

                var jsonstr={"judge_score":judge_score};
                console.log(jsonstr);
                res.json(jsonstr);

        }
        else if(ctg=="떡볶이전문"){
              
                await  preference("떡볶이전문");
                //하위분류점수+선호도 점수 합산값
                console.log(prefer_score+","+category_score[18]);
                judge_score=prefer_score+category_score[18];
                console.log(judge_score);

                var jsonstr={"judge_score":judge_score};
                console.log(jsonstr);
                res.json(jsonstr);

        }
        else if(ctg=="갈비/삼겹살"){
                await  preference("갈비/삼겹살");
                //하위분류점수+선호도 점수 합산값
                console.log(prefer_score+","+category_score[19]);
                judge_score=prefer_score+category_score[19];
                console.log(judge_score);

                var jsonstr={"judge_score":judge_score};
                console.log(jsonstr);
                res.json(jsonstr);

        }
        else if(ctg=="닭/오리요리"){
                await  preference("닭/오리요리");
                //하위분류점수+선호도 점수 합산값
                console.log(prefer_score+","+category_score[20]);
                judge_score=prefer_score+category_score[20];
                console.log(judge_score);

                var jsonstr={"judge_score":judge_score};
                console.log(jsonstr);
                res.json(jsonstr);

        }
});

//이미 개점된 fran은 o 출력 배열을 만들어야하나싶다
app.get('/judgement/result',function(req,res){
        var sql_result="select lowerCategory,fran_name,total_money from francise where lowerCategory="+ctg+" and total_money<"+cost;
        connection.query(sql_result,function(err,rows){
                if(err){
                        console.log(err);
                }
                else{
                        // for(var i=0;i<rows.length;i++){
                        //         if(rows[i].fran_name==""){
                                        
                        //         }
                        // }
                        console.log("판단점수 : "+judge_score);
                        res.json(judge_score,rows);
                        console.log('success!!!!!');
                }
        })
})

var prefer_score;
function preference(pctg){
        //선호도 점수->등수에 따라 점수
        return new Promise(function(resolve,reject){
        var ranking;
        var sql_v="SELECT RANK() OVER (ORDER BY votecnt DESC) as ranking, category,votecnt FROM vote";
        connection.query(sql_v,function(err,rows){
                if(err){
                        console.log(err);
                }
                else{
                        for(var i=0;i<rows.length;i++){
                                if(rows[i].category==pctg){
                                     ranking=rows[i].ranking;
                                     break;   
                                }
                        }
                        //랭킹이 높을 때 선호도 점수를 어떻게 주냐
                        prefer_score=(1/ranking)*2.1;
                        resolve();
                }
        });
});  
}

app.get('/CommercialAnalyze/living/byyear',function(req,res){
        var sql="select year, sum(population) as pop from Living_people group by year;";
        connection.query(sql,function(err,result){
                if(err){
                        console.log(err);
                }
                else{
                        console.log(result);
                        res.json(result);
                        console.log('success!!!!!');
                }
        })
});
app.get('/CommercialAnalyze/living/byage',function(req,res){
        var sql="select age,sum(population) as pop from Living_people group by age;";
        connection.query(sql,function(err,result){
                if(err){
                        console.log(err);
                }
                else{
                        console.log(result);
                        res.json(result);
                        console.log('success!!!!!');
                }
        })
});
app.get('/CommercialAnalyze/living/bygender',function(req,res){
        var sql="select gender,sum(population) as pop from Living_people group by gender;";
        connection.query(sql,function(err,result){
                if(err){
                        console.log(err);
                }
                else{
                        console.log(result);
                        res.json(result);
                        console.log('success!!!!!');
                }
        })
});
app.get('/CommercialAnalyze/vote/byCategory',function(req,res){
        var sql="SELECT RANK() OVER (ORDER BY votecnt DESC) as ranking, category,votecnt FROM vote";
        connection.query(sql,function(err,result){
                if(err){
                        console.log(err);
                }
                else{
                        console.log(result);
                        res.json(result);
                        console.log('success!!!!!');
                }
        })
});
app.get('/CommercialAnalyze/vote/byCategory',function(req,res){
        var sql="SELECT RANK() OVER (ORDER BY votecnt DESC) as ranking, category,votecnt FROM vote";
        connection.query(sql,function(err,result){
                if(err){
                        console.log(err);
                }
                else{
                        console.log(result);
                        res.json(result);
                        console.log('success!!!!!');
                }
        })
});
app.get('/CommercialAnalyze/vote/byFrancise',function(req,res){
        var sql="SELECT RANK() OVER (ORDER BY votecnt DESC) as ranking, value,votecnt FROM francisevote";
        connection.query(sql,function(err,result){
                if(err){
                        console.log(err);
                }
                else{
                        console.log(result);
                        res.json(result);
                        console.log('success!!!!!');
                }
        })
});