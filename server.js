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
        host: 'mydbinstance.csygxgspjzz1.us-east-2.rds.amazonaws.com',
        user:'root',
        database: 'biz',
        password: '12341234',
        port:3306
});


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