package com.laioffer.jupiter.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laioffer.jupiter.entity.Game;
import com.laioffer.jupiter.external.TwitchClient;
import com.laioffer.jupiter.external.TwitchException;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="GameServlet", urlPatterns = {"/game"})
public class GameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject object = new JSONObject(IOUtils.toString(request.getReader()));
        String name = object.getString("name");
        String developer = object.getString("developer");

        System.out.println(name + " " + developer);

        response.setContentType("application/json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", "ok");
        response.getWriter().print(jsonObject);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        JSONObject object = new JSONObject();
//        object.put("name", "World of Warcraft");
//        object.put("developer", "Blizzard Entertainment");
//        object.put("price", 49.99);

        String gameName = request.getParameter("game_name");
        TwitchClient client = new TwitchClient();

        response.setContentType("application/json;charset=UTF-8");
        try {
            if (gameName != null) {
                response.getWriter().print(new ObjectMapper().writeValueAsString(client.searchGame(gameName)));
            } else {
                response.getWriter().print(new ObjectMapper().writeValueAsString(client.topGames(0)));
            }
        } catch (TwitchException e) {
            throw new ServletException(e);
        }

    }

    /**
     * 如果返回一个JSON格式的数据，需要把数据转换成JSON格式。
     *
     * */
}
