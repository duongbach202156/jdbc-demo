package repository.impl;

import entity.City;
import repository.CityRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CityQuery implements CityRepository {

    private Connection connection;

    public CityQuery(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<City> findAll() {
        String selectAll = "select * from city";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAll);
            List<City> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(parseCity(resultSet));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public City findCityById(int id)  {
        String select = "select * from city where id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(select);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return parseCity(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert (City city) {
        String insert = "insert into city values (default, ?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(insert);
            statement.setString(1, city.getName());
            statement.executeUpdate();
            System.out.println("insert successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(String cityName, int id)  {
        String update = "update city set name = ? where id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(update);
            statement.setString(1, cityName);
            statement.setInt(2, id);
            statement.executeUpdate();
            System.out.println("update successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(int id)  {
        String delete = "delete from city where id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(delete);
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("delete successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public City parseCity(ResultSet resultSet) {
        City city = new City();
        try {
            city.setId(resultSet.getInt("id"));
            city.setName(resultSet.getString("name"));
            return city;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void showCity(ResultSet resultSet) throws SQLException {
        System.out.println("Id: " + resultSet.getString("id") + "; "
                + "Name: " + resultSet.getString("name"));
    }

    @Override
    public void insertMultiple(List<City> cityList) {
        String insert = "insert into city values (default, ? ) ";
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(insert);
            for (City city : cityList) {
                preparedStatement.setString(1, city.getName());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        }
    }

    // default TYPE_FORWARD_ONLY
    public void resultSetUpdatable() throws SQLException {
        String select = "select * from city";
        String insert = "insert into city values (default, 'Thai Nguyen')";
        String delete = "delete from city where id = 7";
        Statement statement1 = connection.createStatement();
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = statement.executeQuery(select);
        // resultset can move forward and backward
        resultSet.last();
        resultSet.updateString("name", "Tokyo");
        resultSet.updateRow();
        resultSet.previous();
        resultSet.updateString("name", "New York");
        resultSet.updateRow();
        resultSet.first();


        // insensitive => result set does not have new row inserted
        // sensitive => result set have new row inserted
        while (resultSet.next()) {
            statement1.execute(insert);
            showCity(resultSet);
        }
    }
}
