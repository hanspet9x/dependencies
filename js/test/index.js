const crypto = require('crypto');
const http = require('http');

/* ×
BASE URL
https://remitademo.net/remita/exapp/api/v1/send/api

CREDENTIALS



merchantId: 2547916

apiKey: 1946
SHA-512 hash of merchantId+ serviceTypeId+ orderId+totalAmount+apiKey
serviceTypeId: 4430731 */

const hash = (algorithm, array = []) => {
    return crypto.createHash(algorithm).update(Array.isArray(array) ? array.join('') : array).digest('hex');
}

const mid = 2547916;
const sid = 4430731;
const apiKey = 1946;
const oid = 12;
const amount = 15000;

const writeData = JSON.stringify({ 
	"serviceTypeId": sid,
	"amount": amount,
	"orderId": oid,
	"payerName": "John Doe",
	"payerEmail": "doe@gmail.com",
	"payerPhone": "09062067384",
	"description": "Payment for Septmeber Fees"
});

const hashed = hash('sha512', [mid, sid, oid,amount, apiKey]);
console.log(hashed);

const auth = `remitaConsumerKey=${mid},remitaConsumerToken=${hashed}}`
const cr = http.request({
    hostname: 'remitademo.net',
    method: 'POST',
    path:'/remita/exapp/api/v1/send/api/echannelsvc/merchant/api/paymentinit',
    headers: {
        "Content-Type": "application/json",
        "Authorization": "remitaConsumerKey=2547916,remitaConsumerToken="+auth+""
    }
}, res => {
    res.on('data', data => console.log(data.toString('utf8')));
});

cr.on('response', res => {
    res.on('data', data => console.log(data.toString('utf8')));
});
cr.write(writeData);
cr.end();



