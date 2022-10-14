FROM node:14.17-alpine

WORKDIR /app

COPY card-transfer/package.json /app

RUN npm install

COPY card-transfer/. /app

EXPOSE 3000

CMD ["npm", "run", "start"]