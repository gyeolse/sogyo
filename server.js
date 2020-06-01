//test

var mysql = require('mysql'); 
var express = require('express');
var bodyParser = require('body-parser');
var app = express();
//var config = require('./config.json');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

app.listen(3000,'localhost',function(){
        console.log('server is running....1');
});

var connection = mysql.createConnection({
        host: 'localhost',
        user:'root',
        database: 'biz',
        password: '1234',
        port:3306
});

var sql1="select t1.population, t2.lowerCategory,count(*) as cnt from bizzone as t1 join store as t2 on t1.localNo=t2.bizZone_localNo group by t2.lowerCategory";
connection.query(sql1,function(err,result){
    if(err){
        console.log(err);
    }else{
        for(var i=0;i<result.length;i++){
            console.log(result[i].cnt);
        }
    }
})

//?��권분?�� 그래?�� 분기�? 매출?��
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

app.get('/analysis/details',function(req,res){
        var sql="";
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

        });
});

app.get('/judgement',function(req,res){
        var sql="";
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
})