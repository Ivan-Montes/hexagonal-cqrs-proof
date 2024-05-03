FROM redis:latest

COPY ./init-redis.sh /usr/local/bin/init-redis.sh
COPY ./init-data.redis /usr/local/bin/init-data.redis

CMD ["sh", "/usr/local/bin/init-redis.sh"]