# **Projet Microservices avec Spring Boot**
## **Binôme : AZZOUZI OMAR & TADILI MOHAMED OUASSIM**

## **Description du Projet**
Ce projet met en œuvre une architecture de microservices utilisant les technologies suivantes :
- **Spring Boot** : Framework principal pour le développement des microservices.
- **Eureka Netflix** : Pour l'enregistrement et la découverte des microservices.
- **Spring Cloud** : Gestion centralisée de la configuration.
- **Resilience4j** : Gestion de la résilience avec des mécanismes de tolérance aux pannes.
- **API Gateway** : Point d'accès unique à l'application.
- **MySQL** : Base de données relationnelle utilisée pour le stockage.

## **Structure du Projet**

### **1. Microservice Produit**
- Fonctionnalités CRUD pour la gestion des produits (sans SQL).
- Utilise MySQL pour stocker les données des produits.
- **Resilience4j** a été implémenté dans ce projet pour gérer les mécanismes de résilience, remplaçant **Hystrix**. Ce choix a été motivé par des considérations liées aux versions des technologies utilisées, notamment **Spring Boot**, **Spring Cloud**, et **MySQL**. **Hystrix** étant déprécié, **Resilience4j** s'est imposé comme une alternative moderne, légère et compatible avec les dernières versions de ces outils.
### **Simulation des Timeouts**
- Les timeouts ont été simulés dans le microservice **Produit** en générant une exception conditionnelle. 
- Par exemple, si l'ID du produit est égal à `1`, une exception `RuntimeException` est levée pour déclencher le circuit breaker et activer le mécanisme de fallback.- Endpoints REST pour les opérations sur les produits.
- ![image](https://github.com/user-attachments/assets/16495057-dcc0-4cff-9882-80fa426b48d6)


### **2. Microservice Commandes**
- Fonctionnalités CRUD pour la gestion des commandes, sans écriture directe de SQL.
- La table **Commande** (version 1) contient les colonnes : `id`, `description`, `quantité`, `date`, `montant`.
- Une propriété personnalisée `mes-config-ms.commandes-last`, gérée dans le **Config Server**, permet de définir la période pour afficher les commandes récentes :
  - Par défaut : commandes des 10 derniers jours.
  - ![image](https://github.com/user-attachments/assets/3bb013fb-16fd-49d7-8607-8d11578854e5)
  - ![image](https://github.com/user-attachments/assets/ca0d8645-89f6-4113-b080-c6d6fe82b4fc)
  - Modifiable à chaud via `/actuator/refresh` pour passer à 20 jours.
  - ![image](https://github.com/user-attachments/assets/8e6b46f1-ba1c-4e59-aec5-d46d440cd5cf)
  - ![image](https://github.com/user-attachments/assets/2a049268-0af6-4d35-a669-e888702da209)
  - ![image](https://github.com/user-attachments/assets/e0b780d7-710f-4101-93ca-66927e2c1481)





- Supervision personnalisée de la santé :
  - **UP** : Si des commandes existent dans la base.
  - **DOWN** : Si aucune commande n'est trouvée.
  - ![image](https://github.com/user-attachments/assets/b25a31f6-dac2-4f50-9401-20de7e250387)


### **3. Eureka Server**
- Serveur centralisé pour l'enregistrement et la découverte des microservices.
- Les microservices **Produit** et **Commandes** sont enregistrés automatiquement.
- ![image](https://github.com/user-attachments/assets/03416b87-8933-4f36-b0cb-c566b66aef7d)

### **4. Config Server**
- Centralisation des configurations via un dépôt GitHub.
- Fichiers de configuration :
  - **application.properties** : Configuration générale.
  - **microservice-produit.properties** : Configuration du microservice produit.
  - **microservice-commandes.properties** : Configuration du microservice commandes.
  - ![image](https://github.com/user-attachments/assets/558ab94c-538a-4488-b26a-8ffec2472777)


### **5. API Gateway**
- Point d'entrée unique pour accéder aux différents microservices.
- Redirige les requêtes vers les microservices **Produit** et **Commandes**.
- ![image](https://github.com/user-attachments/assets/a70204f5-b96d-458b-83ae-009b622dd140)
- ![image](https://github.com/user-attachments/assets/5c542d0d-ab30-45c3-b05c-dca4cc0edd6e)
- ![image](https://github.com/user-attachments/assets/9c1c8ea7-e057-44ec-9234-60454d4c670e)




---

