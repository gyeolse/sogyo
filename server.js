
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
        password: '0000',
        port:3306
});

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