package com.digdes.servlets;

//@WebServlet(name="DepartmentTypeServlet", urlPatterns="/types")
//public class DepartmentTypeServlet extends HttpServlet {
//    private final Gson gson = new GsonBuilder().serializeNulls().create();
//    private final DepartmentTypeApiController controller;
//
//    public DepartmentTypeServlet() {
//        controller = new DepartmentTypeApiController();
//    }
//
//    public DepartmentTypeServlet(DepartmentTypeApiController controller) {
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
//            if ( id == null || id.equals("null")) {
//                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//                return;
//            }
//            else {
//                Optional<DepartmentTypeDto> type = controller.get(Long.parseLong(id));
//                result = type.isPresent() ? gson.toJson(type.get()) : "";
//            }
//        }
//        resp.setContentType("application/json");
//        resp.setCharacterEncoding("UTF-8");
//        resp.getWriter().write(result);
//    }
//
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
//        if (method.equals("POST"))
//            result = processPost(reqBody, resp);
//        if (method.equals("PUT"))
//            result = processPut(reqBody, resp);
//        if (method.equals("DELETE"))
//            result = processDelete(reqBody, resp);
//        resp.setContentType("application/json");
//        resp.setCharacterEncoding("UTF-8");
//        resp.getWriter().write(result);
//    }
//
//    private String processPost(String reqBody, HttpServletResponse resp) throws IOException {
//        DepartmentTypeRequestDto requestDto = gson.fromJson(reqBody, DepartmentTypeRequestDto.class);
//        String result = "";
//        if (requestDto.getOperation() == null)
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//        else {
//            DepartmentTypeDto type = new DepartmentTypeDto(requestDto.getId(), requestDto.getName());
//            switch (requestDto.getOperation()) {
//                case ("find"):
//                    result = gson.toJson(controller.find(type));
//                    break;
//                case ("create"):
//                    result = gson.toJson(controller.create(type));
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
//        DepartmentTypeDto type = gson.fromJson(reqBody, DepartmentTypeDto.class);
//        if (type.getId() == null || type.getName() == null)
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//        else
//            result = gson.toJson(controller.update(type));
//        return result;
//    }
//
//    private String processDelete(String reqBody, HttpServletResponse resp) throws IOException {
//        String result = "";
//        DepartmentTypeDto type = gson.fromJson(reqBody, DepartmentTypeDto.class);
//        if (type.getId() == null)
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//        else
//            result = gson.toJson(controller.delete(type));
//        return result;
//    }
//}
