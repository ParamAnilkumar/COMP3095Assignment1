print('START');

db = db.getSiblingDB('event-service');
db.createUser(
    {
        username:'admin',
        password: 'password',
        roles: [{role: 'readWrite', db: 'event-service'}]
    }
);

db.createCollection('user');


print('END');