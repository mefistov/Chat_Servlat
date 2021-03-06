import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "chatServlet", urlPatterns = { "/chat" }, asyncSupported = true)
public class ChatServlet extends HttpServlet {
 
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        AsyncContext aCtx = req.startAsync(req, resp);
        aCtx.setTimeout(1000*60*5L); //5 min timeout
        ServletContext servletContext = req.getServletContext();
        ((Queue<AsyncContext>)servletContext.getAttribute("chatUsers")).add(aCtx);
    }
 
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        AsyncContext aCtx = req.startAsync(req, resp);
        ServletContext servletContext = req.getServletContext();
        String message = req.getParameter("message");
        String username = req.getParameter("username");
        Queue<Message> msgQueue = (Queue<Message>) servletContext.getAttribute("messages");
        msgQueue.add(new Message(message, username));
        aCtx.complete();
    }
 
}