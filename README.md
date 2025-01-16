# MongoDB Atlas Administration API — Service Account Authentication Demo

This repository demonstrates how to call the [MongoDB Atlas Administration API](https://www.mongodb.com/docs/atlas/api/) using Service Account authentication. You can read the accompanying article ['Calling the MongoDB Atlas Admin API - How to do it from Node, Python, and Golang'](https://www.mongodb.com/developer/how-to/{placeholder}) for more information.

## Setup

To connect to the Atlas Administration API, you need to generate an [Service Account](hhttps://www.mongodb.com/docs/atlas/api/service-accounts-overview/). Make sure you add your IP address in the API access list!

Then, export the following environment variables, where `MONGODB_ATLAS_CLIENT_ID` is your _public key_ and `MONGODB_ATLAS_CLIENT_SECRET` is your _private key_.

```bash
export MONGODB_ATLAS_CLIENT_ID=<client_id>
export MONGODB_ATLAS_CLIENT_SECRET<client_secret>
```

## Usage

The repository includes request examples for Python, Node.js and Ruby.

### Python Example

To run the Python example, execute:

```bash
cd python
pip install -r requirements.txt
python ./python-example.py
```

### Node.js Example

To run the Node.js example, execute:

```bash
cd node
npm install
node ./node-example.js
```

### Atlas SDK Golang Example

To run the Golang SDK example, execute:

```bash
cd golang
export MONGODB_ATLAS_ORG_ID=<id of your atlas organiation> # For example 6707a96bc2b066602a4dfdcc
go run ./sdk-example.go
```

## Disclaimer

Examples are not a supported MongoDB product and serve informational purpose only.
External libraries used in the examples are not officially maintained by MongoDB.
Do not use in production.
