package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PositionDto;
import dto.PositionRequestDto;
import controllers.PositionApiController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name="PositionServlet", urlPatterns="/positions")
public class PositionServlet extends HttpServlet {
    private final Gson gson = new GsonBuilder().serializeNulls().create();
    private final PositionApiController controller;

    public PositionServlet() {
        controller = new PositionApiController();
    }

    public PositionServlet(PositionApiController controller) {
        this.controller = controller;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result = "";
        String reqBody = req.getReader().lines().collect(Collectors.joining());
        if (!reqBody.equals("")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (req.getParameterMap().isEmpty()) {
            result = gson.toJson(controller.getAll());
        }
        else {
            String id = req.getParameter("id");
            if ( id == null || id.equals("null")){
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            else {
                Optional<PositionDto> position = controller.get(Long.parseLong(id));
                result = position.isPresent() ? gson.toJson(position.get()) : "";
            }
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String method = req.getMethod();
        String result = "";
        if (!req.getParameterMap().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String reqBody = req.getReader().lines().collect(Collectors.joining());
        if (reqBody.equals("")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (method.equals("POST"))
            result = processPost(reqBody, resp);
        if (method.equals("PUT"))
            result = processPut(reqBody, resp);
        if (method.equals("DELETE"))
            result = processDelete(reqBody, resp);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(result);
    }

    private String processPost(String reqBody, HttpServletResponse resp) throws IOException {
        PositionRequestDto requestDto = gson.fromJson(reqBody, PositionRequestDto.class);
        String result = "";
        if (requestDto.getOperation() == null)
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        else {
            PositionDto position = new PositionDto(requestDto.getId(), requestDto.getName());
            switch (requestDto.getOperation()) {
                case ("find"):
                    result = gson.toJson(controller.find(position));
                    break;
                case ("create"):
                    result = gson.toJson(controller.create(position));
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        return result;
    }

    private String processPut(String reqBody, HttpServletResponse resp) throws IOException {
        String result = "";
        PositionDto position = gson.fromJson(reqBody, PositionDto.class);
        if (position.getId() == null || position.getName() == null)
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        else
            result = gson.toJson(controller.update(position));
        return result;
    }

    private String processDelete(String reqBody, HttpServletResponse resp) throws IOException {
        String result = "";
        PositionDto position = gson.fromJson(reqBody, PositionDto.class);
        if (position.getId() == null)
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        else
            result = gson.toJson(controller.delete(position));
        return result;
    }
}
