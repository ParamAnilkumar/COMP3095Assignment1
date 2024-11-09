print('START');

db = db.getSiblingDB('booking-service');
db.createUser(
    {
        username:'admin',
        password: 'password',
        roles: [{role: 'readWrite', db: 'booking-service'}]
    }
);

db.createCollection('user');
print('END');