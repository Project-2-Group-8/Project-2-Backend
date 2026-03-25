# Hiking Trail API

Our app gives users a way to store hiking trails, and create logs for different trails that include date of completion, time elapsed, a personal rating, and notes.

## Live URL
- Backend API:[ https://project-2-backend-28do.onrender.com/](https://project-2-backend-28do.onrender.com/)

## Repository Links
- Backend Repo: [https://github.com/yourname/backend-repo](https://github.com/Project-2-Group-8/Project-2-Backend)

## Tech Stack
- Springboot
- PostgreSQL
- Supabase Database
- Deployed on Render

## Features
- User authentication
- Routes for interacting with Hike and HikeLog tables
- CRUD operations
- Protected routes

  
## API Endpoints
Auth
GET /api/auth/login — log in a user
GET /api/auth/signup — signup a new user
GET /api/auth/me — returns information about current user
POST /api/auth/logout — log out a user

Hikes
POST /api/hikes— insert new hike
GET /api/hikes/all — get all hikes
GET /api/hikes/:id — get one hike
PUT /api/hikes/:id — update one hike
PATCH /api/hikes/:id — partial update one hike
DELETE /api/hikes/:id — delete one hike

HikeLogs
POST /api/hike-logs — insert new hike-log
GET /api/hike-logs/leaderboard - get all hikes by activity type ordered descending
GET /api/hike-logs//user/:userId — get all hike logs for one user
