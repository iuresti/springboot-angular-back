
##Generate pair of RSA keys

1. `openssl genrsa -out jwt.pem` to generate the pair of keys
2. `openssl rsa -in jwt.pem ` will show the private key
3. `openssl rsa -in jwt.pem -pubout` will show the public key
