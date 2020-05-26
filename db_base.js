var mysql= require('mysql');
var bodyParser= require('body-parser');
//get : req.query.?
//post : req.body.?
var connection= mysql.createConnection({
    host:'localhost',
    user:'root',
    password:'1234',
    database:'biz'
});

connection.connect();

var sql='';

connection.query(sql,function(err,rows){
    if(err){
        console.log(err);
    }else{

        console.log(rows);
    }
});

connection.end();