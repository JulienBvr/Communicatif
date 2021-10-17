# Communicatif
TP 4IF

Architecture :

Côté serveur nous avons un thread d'écoute par client et un thread d'envoi par message reçus à dispatcher
La liste des sockets est dans la classe "ConnexionsTable". Cette classe fait le lien entre les sockets et les noms de groupes.
Cela nous permet de réutiliser les sockets initialisées à la connexion pour l'envoi d'un message.


Côté client nous avons un thread principal qui initie la socket qui lie le client au serveur, lance un thread d'écoute pour les
messages venant du serveur, envoie le pseudo du client au serveur et envoie les messages provenant du terminal

Le format du message à envoyer est de ce type :
Pseudo:message

L'adresse est en dure une fois le client initialisé

TODO :  Création de groupes
        Affichage des communications
        IHM