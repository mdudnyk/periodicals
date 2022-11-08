Periodicals

Short Description of functionaluty:

Implementation of Periodicals system. 
In the system there is a list of publications that are grouped by topic.
The reader can subscribe to one or more publications.
 
For the list of publications it is realized the possibility:
- sort publications by title;
- sort publications by price;
- samples of publications on a specific topic;
- search for publications by title.

The reader registers in the system and has a personal account, which displays 
information about the publications to which he is subscribed. An unregistered 
user cannot subscribe.The reader has a personal account that he can replenish. 
Funds are withdrawn from the account when subscribing to the publication.
The system administrator has the rights:

- adding, deleting and editing publications;
- blocking / unblocking- the user.


Project requirements:

1. Based on the entities of the subject area, create classes that correspond to them.
2. Classes and methods should have names that reflect their functionality, and should be
   divided into packages.
3. The design of the code must comply with the Java Code Convention.
4. Store information about the subject area in a relational database (as a DBMS
   it is recommended to use MySQL or PostgreSQL).
5. To access data, use the JDBC API using a ready-made or
   self-developed connection pool.
6. The application must support work with the Cyrillic alphabet (be multilingual), including at
   storing information in the database:
   a. it should be possible to switch the interface language;
   b. there should be support for input, output and storage of information (in the database),
      recorded in different languages;
   c. choose at least two languages: one based on the Cyrillic alphabet (Ukrainian or Russian),
      the other based on Latin (English).
7. The application architecture should follow the MVC pattern.
8. When implementing business logic, it is necessary to use design templates: Team,
   Strategy, Factory, Builder, Singleton, Front Controller, Observer, Adapter, etc.
9. Using servlets and JSP, implement the functionality given in the statement task.
10. Use Apache Tomcat as a servlet container.
11. On JSP pages, apply tags from the JSTL library and developed own tags (minimum: one
    custom tag library tag and one tag file tag).
12. Implement protection against resending data to the server when updating the page (implement PRG).
13. When developing, use sessions, filters, listeners.
14. The application must implement authentication and authorization, separation of rights
    access of system users to program components. Password encryption is encouraged.
15. Implement an event log into the project using the log4j library.
16. The code must contain documentation comments (all top-level classes, non-trivial methods
    and constructors).
17. The application must be covered by unit tests (minimum coverage percentage of 40%).
    Writing integration tests is encouraged.
18. Implement a pagination mechanism for pages with data.
19. All input fields must have data validation.
20. The application must respond correctly to errors and exceptional situations of various kinds (final
    the user should not see the stack trace on the client side).
21. Independent expansion of the statement of the task in terms of functionality is encouraged! (addition
    captchas, generating reports in various formats, etc.)
22. Use of HTML, CSS, JS frameworks for the user interface (Bootstrap, Materialize,etc.) is encouraged!
