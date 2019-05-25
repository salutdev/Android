import mongodb from 'mongodb';

export default {
  // "port": 3005,
  // "mongoUrl": "mongodb://localhost:27017/chat-api",
  "port": process.env.PORT,
  "mongoUrl": "mongodb+srv://salutdev:<password>@cluster0-8tarj.mongodb.net/chat?retryWrites=true",
  "bodyLimit": "100kb"
}
