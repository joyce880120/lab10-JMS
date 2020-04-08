# 95-702 Distributed Systems
# JMS Lab
# Due: Before your next lab
# Credit is given via Zoom with your TA

# Task 1 – Single Queue

![Task 1 Flow](https://github.com/CMU-Heinz-95702/lab10-JMS/blob/master/task1.png)        

TomEE comes with the JMS implementation ActiveMQ. To use it, you'll use the @Resource annotation in your Java code to automatically create and access a queue (or more – in the other tasks).  Download the Lab10.zip from the Schedule page. Unzip it; there should be two .java file and one .jsp file; you'll need it in steps 5, 6, and 11.

Build an application consisting of a web application and a Message Driven Bean
1. Select File -> New -> Project
2. Select Java Enterprise on the left and Web Application on the right. Make sure the Application Server tab says TomEE.   Click Next.
3. Name your project Lab10 and click Finish. The Project Structure window should come up – if not, choose that from the file menu.
4. Expand the project, right click on src, choose New Java Class. Name the class MyQueueWriter.
5. Copy the code for this class from the downloaded file and paste it over the generated code. If needed – if it's printed in red, hover your cursor over the WebServlet annotation and choose JavaEE 6 and download it.
6. In the project window, expand web and open the default index.jsp. Copy the code for this from the downloaded file and paste it over the generated code.
7. Right click on the Project (Lab10) and choose New Module.
8. Pick Java Enterprise on the left and EJB:Enterprise Java Beans on the right. Again make sure that TomEE is in the Application Server box. Don't worry about the Libraries part below; that will get fixed later. Click Next.
9. Name the module MDB and click Finish.
10. In the project window, expand MDB, right click on its src folder, choose New Java Class. Name the class MyQueueListener.
11. Copy the code for this class from the downloaded file and paste it over the generated code.
12. Take note of where both classes reference the correct queue, called jms/myQueue. These must agree to ensure that the reader reads from the same queue that the writer writes to – a simple issue, but one that you'll be in charge of for later tasks.
13. At the top right, TomEE should be showing in the small text window. Click the down arrow and choose Edit Configurations.
14. On the Server tab, scroll to the bottom to the Build window. Click the + sign and choose Build Artifacts. Click the box for Lab10: war exploded artifact and click Okay. IMPORTANT: Both Lab10: war exploded and MDB: ejb exploded should show up in the Build window – if one of them is missing, click the + sign, Build Artifacts, and choose that one.
15. Scroll up and click the Deployment tab. Both MDB:ejb exploded and Lab10:war exploded should be in the Deploy window. If not, click the + sign at the bottom, click Artifact, and the window should populate with the other artifact.
16. Click on Lab10:war exploded. In the Application content text box, make sure it says "/", without the rest of the Lab10 text. Click OK.
17. Now you can run the application – click the green arrow. After it builds, a new browser window should pop up. Type in your own message in the text box and click the "Send to … " button. The browser should display that <your message> was written to a queue. More importantly, the Run – Output window in Intellij should say, Servlet sent <your message> to queue, and MyQueueListener received: <your message>. That last print statement is triggered by the queue listener reacting to the queue event.

That's a lot of steps. If something isn't working, here's a list of things to check on before seeking help:
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

:checkered_flag:**CHECKPOINT: Completion of Task 1 is the lab checkpoint.**


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

:checkered_flag:**Show working Task 2 and Task 3 to your TA**
