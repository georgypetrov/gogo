# JUnit tests - Payment transaction gateway
QA sanity check application for Payment transaction gateway

1. Set up the Payment Gateway API locally using this github repo
https://github.com/eMerchantPay/codemonsters_api_full
2. Clone this repo
3. Inport project in Eclipse
4. Run with JUnit4

Sanity checks Application, implemented tests:
- send a valid payment transaction request and expect an approved
response
- send a valid void transaction request and expect an approved response
- send a valid payment transaction with an invalid authentication and expect
an appropriate response (401 etc)
- send a void transaction pointing to a non-existent payment transaction
and expect 422 etc
