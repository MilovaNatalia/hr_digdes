package com.digdes.servlets;

//@WebServlet(name="DepartmentsServlet", urlPatterns="/departments")
//public class DepartmentServlet extends HttpServlet {
//
//    private final Gson gson = new GsonBuilder().serializeNulls().create();
//    private final DepartmentApiController controller = new DepartmentApiController();
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
//            if ( id == null || id.equals("null"))
//                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//            else {
//                Optional<DepartmentResponseDto> department = controller.get(Long.parseLong(id));
//                result = department.isPresent() ? gson.toJson(department.get()) : "";
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
//        DepartmentRequestDto requestDto = gson.fromJson(reqBody, DepartmentRequestDto.class);
//        String result = "";
//        if (requestDto.getOperation() == null)
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//        else {
//            DepartmentDto department = Mapper.mapDepartmentDtoFromRequest(requestDto);
//            switch (requestDto.getOperation()) {
//                case ("find"):
//                    result = gson.toJson(controller.find(department));
//                    break;
//                case ("create"):
//                    result = gson.toJson(controller.create(department));
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
//        DepartmentDto department = gson.fromJson(reqBody, DepartmentDto.class);
//        if (department.getId() == null || (department.getName() == null && department.getTypeId() == null))
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//        else
//            result = gson.toJson(controller.update(department));
//        return result;
//    }
//
//    private String processDelete(String reqBody, HttpServletResponse resp) throws IOException {
//        String result = "";
//        DepartmentDto department = gson.fromJson(reqBody, DepartmentDto.class);
//        if (department.getId() == null)
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//        else
//            result = gson.toJson(controller.delete(department));
//        return result;
//    }
//}
