# Use an official Node runtime as a parent image
FROM node:14-alpine

# Set the working directory in the container
WORKDIR /pet-feeder

# Copy package.json and package-lock.json to the working directory
COPY public/ /pet-feeder/public
COPY src/ /pet-feeder/src
COPY package.json /pet-feeder/

# Install dependencies
RUN npm install

CMD ["npm", "start"]
