# Fun Unified Payments API

This is a first class payments infrastructure to allow any company wanting to process payments with a set of payment
providers. The purpose it's to encapsulate the complexity of the payment gateway providing a straightforward API
while making it very easy to integrate with new payment providers.

The main tool used to develop this project is [JHipster](https://www.jhipster.tech/). It's development platform that helps you
bootstrapping all the different frameworks required to build modern web applications.

The strategy chosen was to divide the project into small chunks that could be implemented separately and then glue them
all together. Some of those are:

- Understand current PCI DSS requirements for storage of cardholder data.
- Unique transaction reference and token generation.
- Learn how to use JHipster. Using [JDL Studio](https://start.jhipster.tech/jdl-studio/) to model the entities and relationships.
- Define the [ER model](https://github.com/miguel-suarez/unified-payments-api/blob/master/documentation/er_diagram.png).
- Define the [API documentation](https://github.com/miguel-suarez/unified-payments-api/blob/master/documentation/api.yml).
- Implement the [Sale API](https://github.com/miguel-suarez/unified-payments-api/blob/master/src/main/java/com/fun/driven/development/fun/unified/payments/api/web/rest/PaymentResource.java).
- Implement the [Tokenization API](https://github.com/miguel-suarez/unified-payments-api/blob/master/src/main/java/com/fun/driven/development/fun/unified/payments/api/web/rest/TokenResource.java).
- Design and implement the Payments Gateway.
- Integrate Braintree as a Payment processor.
- Integrate a strong cryptographic library.
- Bolt it all together!

## Development

Currently, there's an open [issue](https://github.com/miguel-suarez/unified-payments-api/issues/11) to fix the local
development environment. Once it's fixed you can start your application in the dev profile by running:

```
./gradlew
```

## Testing

To launch your application's tests, run:

```
./gradlew test integrationTest
```

## Deploying to production

### Heroku CLI installation, and first deployment

Before you deploy the project for the first time you need to install heroku CLI and run the jhipster heroku configuration step:

```
sudo snap install --classic heroku
jhipster heroku
```

### Subsequent deployments

To build the final jar and optimize the FunUnifiedPaymentsApi application for production, run:

```
git push heroku master
```

To check the logs, run:

```
heroku logs --tail
```

### To be done in future releases

- http requests must be automatically redirected to https
- Validate token expiry date and payment method unavailable periods before transaction
- Turn payment gateway and vault into a java modules
- Improve Sale response time
- Automatic retry
- Fallback retry, in case of unavailable payment processor
- Fully i18n the API response messages
