package com.getjavajobs.library.dao;

import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.model.Genre;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO implements GenericDao<Genre> {

    private ConnectionHolder connectionHolder;

    public void setConnectionHolder(ConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;
    }

    @Override
    public Genre add(Genre genre) {
        Connection connection = connectionHolder.getConnection();
        boolean success = true;
        String command = "INSERT INTO Genre(genre_type) values(?);";

        try (PreparedStatement addStatement = connection.prepareStatement(command);) {
            addStatement.setString(1, genre.getGenreType());

            addStatement.execute();
            genre.setId(getLastId(connection));
        } catch (SQLException e) {
            success = false;
            throw new DAOException(e.getMessage(), e);
        } finally {
            try {
                if (!connection.getAutoCommit()) {
                    if (success) {
                        connection.commit();
                    } else {
                        connection.rollback();
                    }
                }
            } catch (SQLException e) {
                throw new DAOException(e.getMessage(), e);
            } finally {
                connectionHolder.releaseConnection();
            }
        }

        return genre;
    }

    @Override
    public void delete(int id) {
        Connection connection = connectionHolder.getConnection();
        boolean success = true;
        try (PreparedStatement statement = connection.prepareStatement("delete from Genre where id=?;");) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            success = false;
            throw new DAOException(e.getMessage(), e);
        } finally {
            try {
                if (!connection.getAutoCommit()) {
                    if (success) {
                        connection.commit();
                    } else {
                        connection.rollback();
                    }
                }
            } catch (SQLException e) {
                throw new DAOException(e.getMessage(), e);
            } finally {
                connectionHolder.releaseConnection();
            }
        }
    }

    @Override
    public Genre get(int id) {
        Connection connection = connectionHolder.getConnection();
        boolean success = true;
        try (PreparedStatement statement = connection.prepareStatement("select * from genre where id=?;");) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return resultsSetToGenre(resultSet);
        } catch (SQLException e) {
            success = false;
            throw new DAOException(e.getMessage(), e);
        } finally {
            try {
                if (!connection.getAutoCommit()) {
                    if (success) {
                        connection.commit();
                    } else {
                        connection.rollback();
                    }
                }
            } catch (SQLException e) {
                throw new DAOException(e.getMessage(), e);
            } finally {
                connectionHolder.releaseConnection();
            }
        }
    }

    @Override
    public Genre update(Genre genre) {
        Connection connection = connectionHolder.getConnection();
        boolean success = true;
        String command = "UPDATE genre SET "
                + "genre_type=? where id=?";
        try (PreparedStatement statement = connection.prepareStatement(command)) {
            statement.setString(1, genre.getGenreType());
            statement.setInt(2, genre.getId());
            statement.execute();
        } catch (SQLException e) {
            success = false;
            throw new DAOException(e);
        } finally {
            try {
                if (!connection.getAutoCommit()) {
                    if (success) {
                        connection.commit();
                    } else {
                        connection.rollback();
                    }
                }
            } catch (SQLException e) {
                throw new DAOException(e.getMessage(), e);
            } finally {
                connectionHolder.releaseConnection();
            }
        }
        Genre returnedGenre = get(genre.getId());
        if (returnedGenre == null) {
            throw new DAOException("IncorrectId");
        }
        return returnedGenre;
    }

    @Override
    public List<Genre> getAll() {
        Connection connection = connectionHolder.getConnection();
        boolean success = true;
        List<Genre> genres = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books;");
            while (resultSet.next()) {
                genres.add(resultsSetToGenre(resultSet));
            }
        } catch (SQLException e) {
            success = false;
            throw new DAOException(e.getMessage(), e);
        } finally {
            try {
                if (!connection.getAutoCommit()) {
                    if (success) {
                        connection.commit();
                    } else {
                        connection.rollback();
                    }
                }
            } catch (SQLException e) {
                throw new DAOException(e.getMessage(), e);
            } finally {
                connectionHolder.releaseConnection();
            }
        }

        return genres;
    }

    private Genre resultsSetToGenre(ResultSet resultSet) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getInt("id"));
        genre.setGenreType(resultSet.getString("genre_type"));
        return genre;
    }

    private int getLastId(Connection connection) throws SQLException {
        Statement lastIdStatement = connection.createStatement();
        ResultSet resultSet = lastIdStatement.executeQuery("SELECT last_insert_id();");
        resultSet.next();
        int lastId = resultSet.getInt(1);
        lastIdStatement.close();
        return lastId;
    }
}
