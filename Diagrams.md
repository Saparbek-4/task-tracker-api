```erDiagram

User {
int id PK
string name
string email
string password
boolean is_admin
int loyalty_points
string loyalty_tier
}

UserPreference {
int id PK
int user_id FK
string preferred_cuisine
string dietary_restrictions
string ambiance_preference
}

Restaurant {
int id PK
string name
string location
string cuisine
int capacity
int booking_duration
float average_price
float lat
float lon
json features
time opening_time
time closing_time
string promo
}

RestaurantImage {
int id PK
int restaurant_id FK
string image_url
}

MenuItem {
int id PK
int restaurant_id FK
string category
string name
string description
float price
string image_url
}

Layout {
int id PK
int restaurant_id FK
string type
int table_number
string table_type
float x_coordinate
float y_coordinate
string shape
int capacity
string name
float width
float height
string color
}

LayoutVersion {
int id PK
int restaurant_id FK
datetime created_at
json layout_data
string description
boolean is_suggestion
}

Booking {
int id PK
int user_id FK
int restaurant_id FK
int layout_id FK
int num_guests
string status
string special_requests
datetime date
json menu_orders
}

Review {
int id PK
int user_id FK
int restaurant_id FK
int rating
string comment
datetime date_created
}

Payment {
int id PK
int booking_id FK
float amount
string status
datetime created_at
}

%% Relationships
User ||--o{ UserPreference : has
User ||--o{ Booking : makes
User ||--o{ Review : writes

Restaurant ||--o{ RestaurantImage : has
Restaurant ||--o{ MenuItem : offers
Restaurant ||--o{ Layout : contains
Restaurant ||--o{ LayoutVersion : saves
Restaurant ||--o{ Booking : receives
Restaurant ||--o{ Review : receives

Booking ||--|| Payment : has
Booking ||--o{ MenuItem : includes
Booking ||--|| Layout : uses

Layout ||--o{ Booking : reserved_by
