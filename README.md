# apigee-googleapis-javaclient-demo

This is a demo repo that provides more details for this [Google Cloud Community post](https://www.googlecloudcommunity.com/gc/Apigee/Unable-to-associate-DeveloperAppKey-with-API-Products-using/m-p/802502#M80429).


## Summary
This code will 
1. create a new Developer App Key
2. Update that key to include the products that you include
3. Fetch the Developer App Key to show that it has been updated


## Prerequistes.
You should complete the following in the Apigee UI:
1. Create an Apigee Proxy
2. Create one or more Apigee Products


## How to use this code?
You must first execute the following command before you execute this code.  This will download the Google Cloud default 
application credentials to your local machine and the Java code will use those credentials during its execution. 

```shell
gcloud auth application-default login
```

Then execute it in your favorite IDE. 