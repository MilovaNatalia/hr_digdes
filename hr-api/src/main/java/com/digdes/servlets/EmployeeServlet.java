//package com.digdes.servlets;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.digdes.controllers.EmployeeApiController;
//import com.digdes.dto.EmployeeDto;
//import com.digdes.dto.EmployeeRequestDto;
//import com.digdes.dto.EmployeeResponseDto;
//import com.digdes.util.Mapper;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@WebServlet(name="EmployeesServlet", urlPatterns="/employees")
//public class EmployeeServlet extends HttpServlet {
//    private final Gson gson = new GsonBuilder().serializeNulls().create();
//    private final EmployeeApiController controller;
//
//    public EmployeeServlet() {
//        controller = new EmployeeApiController();
//    }
//
//    public EmployeeServlet(EmployeeApiController controller) {
//        this.controller = controller;
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String result = "";
//        String reqBody = req.getReader().lines().collect(Collectors.joining());
//        if (!reqBody.equals("")){
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//            return;
//        }
//        if (req.getParameterMap().isEmpty()) {
//            result = gson.toJson(controller.getAll());
//        }
//        else {
//            String id = req.getParameter("id");
//            if ( id == null || id.equals("null")){
//                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//                return;
//            }
//            else {
//                Optional<EmployeeResponseDto> employee = controller.get(Long.parseLong(id));
//                result = employee.isPresent() ? gson.toJson(employee.get()) : "";
//            }
//        }
//        resp.setContentType("application/json");
//        resp.setCharacterEncoding("UTF-8");
//        resp.getWriter().write(result);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        processRequest(req, resp);
//    }
//
//    @Override
//    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        processRequest(req, resp);
//    }
//
//    @Override
//    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        processRequest(req, resp);
//    }
//
//    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        String method = req.getMethod();
//        String result = "";
//        if (!req.getParameterMap().isEmpty()) {
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//            return;
//        }
//        String reqBody = req.getReader().lines().collect(Collectors.joining());
//        if (reqBody.equals("")){
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//            return;
//        }
//        switch (method) {
//            case "POST":
//                result = processPost(reqBody, resp);
//                break;
//            case "PUT":
//                result = processPut(reqBody, resp);
//                break;
//            case "DELETE":
//                result = processDelete(reqBody, resp);
//                break;
//        }
//        resp.setContentType("application/json");
//        resp.setCharacterEncoding("UTF-8");
//        resp.getWriter().write(result);
//    }
//
//    private String processPost(String reqBody, HttpServletResponse resp) throws IOException {
//        EmployeeRequestDto requestDto = gson.fromJson(reqBody, EmployeeRequestDto.class);
//        String result = "";
//        if (requestDto.getOperation() == null)
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//        else {
//            EmployeeDto employee = Mapper.mapEmployeeDtoFromRequest(requestDto);
//            switch (requestDto.getOperation()) {
//                case ("find"):
//                    result = gson.toJson(controller.find(employee));
//                    break;
//                case ("create"):
//                    result = gson.toJson(controller.create(employee));
//                    break;
//                default:
//                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//            }
//        }
//        return result;
//    }
//
//    private String processPut(String reqBody, HttpServletResponse resp) throws IOException {
//        String result = "";
//        EmployeeDto employee = gson.fromJson(reqBody, EmployeeDto.class);
//        if (employee.getId() == null ||
//                (employee.getFirstName() == null && employee.getLastName() == null && employee.getPatronymic() == null
//                        && employee.getEmail() == null && employee.getDepartmentId() == null
//                        && employee.getPositionId() == null && employee.getGender() == null
//                        && employee.getBirthDate() == null))
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//        else
//            result = gson.toJson(controller.update(employee));
//        return result;
//    }
//
//    private String processDelete(String reqBody, HttpServletResponse resp) throws IOException {
//        String result = "";
//        EmployeeDto employee = gson.fromJson(reqBody, EmployeeDto.class);
//        if (employee.getId() == null)
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//        else
//            result = gson.toJson(controller.delete(employee));
//        return result;
//    }
//}
