# RentHub Vehicle Rental System - Deployment Guide

## Frontend (Netlify)

To deploy the frontend on Netlify:

1. **Connect your Git repository** to Netlify
2. **Build settings:**
   - Build command: `npm run build` (if using a build tool) or leave empty
   - Publish directory: `src/main/resources/static`

3. **Environment Variables:**
   - Add `REACT_APP_API_URL` with your backend URL
   - Example: `https://your-backend-api.com/api`

4. **Deploy:**
   - Push to main branch, Netlify will auto-deploy

## Backend (Spring Boot)

The backend needs to be deployed separately on:
- Render.com
- AWS
- Heroku
- Railway.app
- DigitalOcean

Then set the `REACT_APP_API_URL` to your backend URL on Netlify.

## CORS Configuration

Update your Spring Boot application.properties:
```
server.servlet.context-path=/
```

And enable CORS in your Spring Boot controller:
```java
@CrossOrigin(origins = "https://your-netlify-site.netlify.app")
```
