# React, Redux, Strapi, Stripe

## Running the Server and Client

run `npm install` in both directories, and then `npm start`.

Strapi is located in `localhost:1337` as default, while client is located on `localhost:3000` by default.

## Deployment (Client)

```
npx create-react-app client

npm i @mui/material @emotion/react @emotion/styled @mui/icons-material react-router-dom@6 react-redux @reduxjs/toolkit formik yup dotenv react-responsive-carousel 
```

## Development (Server)

Command to start
```
npx create-react-strapi-app@latest <name_of_project>
npm i @stripe/stripe-js
```

## Extensions

ReactJS snippets

node v16.17.1
npm 8.15

Tailwind CSS shades
Tailwind CSS linter

Prettier formatter

## Strapi Configurations

Log into Strapi and create Items, Orders

Under Content-Type Builder, create Item and configure
| NAME             | TYPE        |
| ---------------- | ----------- |
| name             | Text        |
| shortDescription | Rich text   |
| longDescription  | Rich text   |
| price            | Number      |
| image            | Media       |
| category         | Enumeration |

And under Content Manager, add your entries for items. Be sure to click on "Publish". This will allow items to be 
fetched from the API.

Upload your photos to be used by your item catelogue under "Media Library"

### Authentication

To allow the public to access the Strapi API, under "Settings", under "USERS & PERMISSIONS PLUGIN", click on "Roles"
Under "Public", select "Item" and click "find" and "findOne". While for "Order", select "create". 

Finally, select "save" at the top right.


## Inspiration

Inspiration from EdRoh [here](https://github.com/ed-roh/react-ecommerce)
