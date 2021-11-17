# 95-702 Distributed Systems
# JMS Lab

# Task 1 – Single Queue

![Task 1 Flow](diagrams/task1.png)        

TomEE comes with the JMS implementation of Apache ActiveMQ. To use it, you'll use the @Resource annotation in your Java code to automatically create and access a queue (or more – in the other tasks).  

1. From this GitHub repository, click "Code", then copy the URL to Clone the repository.
2. Start IntelliJ, and from the "Welcome to IntelliJ IDEA" window, choose "Get from Version Control"
3. Paste the GitHub repository URL into the dialog box (change the Directory if you like) and select "Clone".

4. When asked whether to "Trust Maven Project", click "Trust Project".
5. A popup window may say "Frameworks Detected", but you can ignore and close it.
6. Click on Run, and you should eventually be sent to a browswer with the URL http://localhost:8080/lab-10/
7. Enter a message in the input box, replacing "Enter text here", and click on "Submit text to servlet".
8. You should get a message that your text has been written to the queue.
9. Look at the TomEE console in IntelliJ, and you should see two messages
 - "Servlet sent" your text "to jms/myQueue"
 - "MyQueueListener received:" your text







---

In Task 1, you'll be writing a Servlet - nothing special about that at this point - but it will take input from your web browser (that's code in the JSP file) and write it to a *message queue* (see the diagram). The queue will be created for you using the annotations. There will be a second program, an Enterprise Java Bean that will read from that queue and simply output the message to the console.

## Build an application consisting of a web application and a Message Driven Bean.
IntelliJ 2020 has a different interface for creating projects. The basic directions for Task 1 (up to the checkpoint) are for IntelliJ 2019; I've added some details for 2020 in brackets.

**Servlet**
1. Select File -> New -> Project
2. Select Java Enterprise on the left and Web Application on the right. [2020: Choose Servlet on the *next* screen.] Make sure the Application Server tab says TomEE.   Click Next.
3. Choose JavaEE and click next. Name your project Lab10 and click Finish. The Project Structure window should come up – if not, choose that from the file menu.
4. Expand the project, right click on src. [2020: put code under src->main->java; remove com.example.Lab10 and HelloServlet (if present).] Choose New Java Class. Name the class MyQueueWriter.
5. Copy the code for this class from the downloaded file and paste it over the generated code. If needed – if it's printed in red, hover your cursor over something in red and choose JavaEE 6 and download it.
6. In the project window, expand web [2020: webapp] and open the default index.jsp. Copy the code for this from the downloaded file and paste it over the generated code.

**Message Driven Bean**

7. In the Project window at the top, right click on the Project (Lab10) and choose New Module.
8. Pick Java Enterprise on the left and EJB:Enterprise Java Beans on the right (in the  Libraries and Frameworks section) [2020: Libraries and Frameworks is on the next screen; check the box for Enterprise Java Beans and uncheck the Servlet box.] Again make sure that TomEE is in the Application Server box. Don't worry about the *Use Library* part below; that will get fixed later. Click Next.
9. Name the module MDB and click Finish.
10. In the project window, expand MDB, right click on its src folder [2020: src->main->java], choose New Java Class. Name the class MyQueueListener.
11. Copy the code for this class from the downloaded file and paste it over the generated code. If any code is red, fix it the same way as in step 5.
12. Take note of where both classes reference the correct queue, called jms/myQueue. These must agree to ensure that the reader reads from the same queue that the writer writes to – a simple issue, but one that you'll be in charge of for later tasks.
13. [2020: Right click on MDB in the Project window. Choose "Add Frameworks Support". Check the box for "EJB:Enterprise Java Beans" and click OK.] At the top right, TomEE should be showing in the small text window. Click the down arrow and choose Edit Configurations.
14. On the Server tab, scroll to the bottom to the Build window. Click the + sign and choose Build Artifacts. Click the box for Lab10: war exploded artifact and click Okay. IMPORTANT: Both Lab10: war exploded and MDB: ejb exploded should show up in the Build window – if one of them is missing, click the + sign, then Build Artifacts, and choose both by checking the boxes. Highlight "Build 2 Artifacts" and click Apply.
15. Scroll up and click the Deployment tab. Both MDB:ejb exploded and Lab10:war exploded should be in the Deploy window. If not, click the + sign at the bottom, click Artifact, and the window should populate with the other artifact.
16. Click on Lab10:war exploded. In the Application content text box, make sure it says "/", without the rest of the Lab10 text. Click OK.

**Test the Queue**

17. Now you can run the application – click the green arrow. After it builds (it may take a few moments), a new browser window should pop up. Type in your own message in the text box and click the "Send to … " button. The browser should display that <your message> was written to a queue. More importantly, the Server Output window in Intellij should say, Servlet sent <your message> to queue, and MyQueueListener received: <your message>. (***Note:*** this window will have a lot of system messages in it; scroll around to find MyQueueListener's output). That last print statement is triggered by the queue listener reacting to the queue event.

That's a lot of steps. If something isn't working, here's a list of things to check on before seeking help:
  - Are you using IntelliJ 2020? Make sure you used the special 2020 instructions!
In the File->Project Structure
  - Make sure the Project SDK is set (sometimes it does not seem to be)
  - Web module exists, and the source root points to the right place
  - EJB module exists, the source root points to the right place (which should be different than the web module)
  - Check each Facet (there should be two) by clicking on them and make sure no libraries are missing and if so Fix.
  - Make sure there are two Artifacts, a "war exploded" and an "ejb exploded"
In the Run/Debug Configurations, Deployment tab,
   - in the Build area (lower part of the dialog box) make sure 2 artifacts are built, and Edit them to make sure they are the war and the ejb
   - in the Deploy area, make sure both Artifacts are listed to deploy
   - also in the Deploy area, single click on the war and make sure the Application Context is what you want it to be (e.g. "/")

:checkered_flag: **CHECKPOINT: Completion of Task 1 is the lab checkpoint.**


# Task 2 – Two Phase Process

![Task 2 Flow](https://github.com/CMU-Heinz-95702/lab10-JMS/blob/master/task2.png)

Modify your project like this:
1. MyQueueListener adds text to the message (e.g. <received text> + " after processing by MyQueueListener") and sends the new message to a new Queue named jms/myQueueTwo.
2. Create a new class MyQueueListener2 modeled after MyQueueListener, but listening to the new queue.
3. Modify the string written to the console so you know the string is coming from MyQueueListener2.
4. Add code to MyQueueListener to send the message to myQueueTwo; this code is similar to that used in MyQueueWriter to write to myQueue.

# Task 3 – Servlet Reading From a Queue

![Task 3 Flow](https://github.com/CMU-Heinz-95702/lab10-JMS/blob/master/task3.png)

1. Create a new Queue jms/myQueueThree.
2. Modify MyQueueListener to write to myQueueThree instead of myQueueTwo.
3. Write a new Servlet called FetchResponses that reads all available messages in myQueueThree and displays them on a web page.
a. If no messages are available, the servlet should clearly state that on the response page.
b. If there are one or more messages in the Queue, all should be displayed.
c. Be sure to start the connection before trying to consume messages from it.
d. The code to read from a Queue is very similar to writing to one, only:
- use createConsumer instead of createProducer
- use receive to get a message instead of send
- use receive(1000) to have the receive time out if no messages are available.

:checkered_flag: **Show working Task 2 and Task 3 to your TA**
