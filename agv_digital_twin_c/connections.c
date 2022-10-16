#include "connections.h"
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>

#include <openssl/crypto.h>
#include <openssl/ssl.h>
#include <openssl/err.h>
#include <openssl/conf.h>
#include <openssl/x509.h>

#define SERVER_SSL_CERT_FILE "../certs/server_agvmanager.pem"

#define BUF_SIZE 60
#define SERVER_PORT "9999"
#define NAME "Srv_1"

SSL *createAgvManagerConnection(char *ip, char *port)
{
    printf("Here");
    int err, sock;
    char line[BUF_SIZE];
    struct addrinfo req, *list;

    bzero((char *)&req, sizeof(req));
    // let getaddrinfo set the family depending on the supplied server address
    req.ai_family = AF_UNSPEC;
    req.ai_socktype = SOCK_STREAM;
    err = getaddrinfo(ip, port, &req, &list);
    if (err)
    {
        printf("Failed to get server address, error: %s\n", gai_strerror(err));
        exit(1);
    }

    sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol);
    if (sock == -1)
    {
        perror("Failed to open socket");
        freeaddrinfo(list);
        exit(1);
    }

    if (connect(sock, (struct sockaddr *)list->ai_addr, list->ai_addrlen) == -1)
    {
        perror("Failed connect");
        freeaddrinfo(list);
        close(sock);
        exit(1);
    }
    puts("Connected");

    const SSL_METHOD *method = SSLv23_client_method();
    SSL_CTX *ctx = SSL_CTX_new(method);

    // Load client's certificate and key
    strcpy(line, "../certs/agv_digital_twin_server.pem");
    SSL_CTX_use_certificate_file(ctx, line, SSL_FILETYPE_PEM);
    strcpy(line, "../certs/agv_digital_twin_server.key");
    SSL_CTX_use_PrivateKey_file(ctx, line, SSL_FILETYPE_PEM);
    if (!SSL_CTX_check_private_key(ctx))
    {
        puts("Error loading client's certificate/key");
        close(sock);
        exit(1);
    }

    SSL_CTX_set_verify(ctx, SSL_VERIFY_PEER, NULL);

    // THE SERVER'S CERTIFICATE IS TRUSTED
    SSL_CTX_load_verify_locations(ctx, SERVER_SSL_CERT_FILE, NULL);

    // Restrict TLS version and cypher suites
    SSL_CTX_set_min_proto_version(ctx, TLS1_2_VERSION);
    SSL_CTX_set_cipher_list(ctx, "HIGH:!aNULL:!kRSA:!PSK:!SRP:!MD5:!RC4");

    SSL *sslConn = SSL_new(ctx);
    SSL_set_fd(sslConn, sock);
    if (SSL_connect(sslConn) != 1)
    {
        puts("TLS handshake error");
        SSL_free(sslConn);
        close(sock);
        exit(1);
    }
    printf("TLS version: %s\nCypher suite: %s\n", SSL_get_cipher_version(sslConn), SSL_get_cipher(sslConn));

    if (SSL_get_verify_result(sslConn) != X509_V_OK)
    {
        puts("Sorry: invalid server certificate");
        SSL_free(sslConn);
        close(sock);
        exit(1);
    }

    X509 *cert = SSL_get_peer_certificate(sslConn);
    X509_free(cert);

    if (cert == NULL)
    {
        puts("Sorry: no certificate provided by the server");
        SSL_free(sslConn);
        close(sock);
        exit(1);
    }

    return sslConn;
}