
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

app.post('/history/location', function (req, res) {
//    var userEmail = req.body.userEmail;
//    var userPwd = req.body.userPwd;
//    var userName = req.body.userName;

    // 삽입을 수행하는 sql문.
    var sql = 'select longitude, latitude from store';    
// sql 문의 ?는 두번째 매개변수로 넘겨진 params의 값으로 치환된다.
    connection.query(sql, function (err, result) {
        var resultCode = 404;
        var message = 'error is occur ';

        if (err) {
            console.log(err);
        } else {
            resultCode = 200;
            message = 'success ';
        }

        res.json({
            'code': resultCode,
            'message': message
        });
    });
});

app.get('/history/location',function(req,res){
//        console.log(req.body);
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
                        res.send(result);
//                        const jsonresult= JSON.parse(result);
//                        res.json(jsonresult);

//                        res.json(result);
//                        JSON.parse(res);
                }

        });
});