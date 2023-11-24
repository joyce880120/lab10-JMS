package ds.web;

import jakarta.annotation.Resource;
import jakarta.jms.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/*
 * ds.web.FetchResponses is a Servlet that takes the value of the simpleTextMessage parameter in a GET request
 * and sends it as a message to a Queue.
 */
@WebServlet(name = "ds.web.FetchResponses", urlPatterns = {"/FetchResponses"})
public class FetchResponses extends HttpServlet {

    // Lookup the ConnectionFactory using resource injection and assign to cf
    @Resource(mappedName = "jms/myConnectionFactory")
    private ConnectionFactory cf;

    // Lookup the Queue using resource injection and assign to q
    @Resource(mappedName = "jms/myQueue3")
    private Queue q;

    private Connection con;
    private Session session;
    private MessageConsumer reader;

    @Override
    public void init() throws ServletException {
        try {
            // With the ConnectionFactory, establish a Connection
            con = cf.createConnection();
            // Establish a Session on that Connection
            session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // Be sure to start the connection
            con.start();
            // Create a MessageConsumer that will read from q
            reader = session.createConsumer(q);
        } catch (JMSException e) {
            throw new ServletException("JMS Exception in init(): " + e.getMessage(), e);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        /*
         * You can now try to receive a message from q.  If you give a
         * time argument to receive, it will time out in that many milliseconds.
         * In this way you can receive until there are no more messages to be read
         * at this time from the q.
         */
        TextMessage tm = null;
        while (true) {
            try {
                if (!((tm = (TextMessage) reader.receive(1000)) != null)) break;
                out.println("<p>" + tm.getText() + "</p>");
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
