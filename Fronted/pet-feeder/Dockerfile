# Use an official Node runtime as a parent image
FROM node:14-alpine

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy package.json and package-lock.json to the working directory
COPY ./PetFeeder/Fronted/pet-feeder/package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code to the working directory
COPY ./PetFeeder/Fronted/pet-feeder/src ./src
COPY ./PetFeeder/Fronted/pet-feeder/public ./public

# Build the application
RUN npm run build

# Specify the command to run on container startup
CMD ["npm", "start"]
