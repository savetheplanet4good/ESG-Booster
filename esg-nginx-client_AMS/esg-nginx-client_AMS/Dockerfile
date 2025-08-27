FROM nginx:1.22

RUN echo "daemon off;" >> /etc/nginx/nginx.conf
RUN rm /etc/nginx/conf.d/default.conf

# SSL certificate are stored in git repo
COPY ssl /etc/ssl/private
COPY nginx.conf /etc/nginx/
COPY conf.d /etc/nginx/conf.d
WORKDIR /etc/nginx/conf.d

EXPOSE 443
ENTRYPOINT ["/usr/sbin/nginx"]