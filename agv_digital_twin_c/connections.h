#if !defined(_CONNECTIONS_H)
#define _CONNECTIONS_H

#include <openssl/crypto.h>

SSL *createAgvManagerConnection(char *ip, char *port);

#endif // _CONNECTIONS_H
