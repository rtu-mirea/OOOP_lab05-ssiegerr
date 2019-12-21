package com.company;
import java.io.*;
import java.util.ArrayList;

public class Main {
    public static ArrayList<User> users = new ArrayList<User>();
    public static ArrayList<Request> employerRequests = new ArrayList<Request>();
    public static ArrayList<Request> employeeRequests = new ArrayList<Request>();

    public static void main(String[] args) {
        load();
        new LogInForm();
    }

    public static Request FindEmployee(String str){
        for (Request request : employeeRequests){
            if (str.equals(request.getRequester().getName())) {
                return request;
            }
        }
        return null;
    }

    public static Request FindEmployer(String str){
        for (Request request : employerRequests){
            if (str.equals(request.getRequester().getName())) {
                return request;
            }
        }
        return null;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static User findUser(String login, String password) {
        for (User user : users) {
            if (user.enter(login, password)) {
                return user;
            }
        }
        return null;
    }

    public static void save() {
        try (
                FileOutputStream usersFile = new FileOutputStream("users.txt", false);
                ObjectOutputStream oosUser = new ObjectOutputStream(usersFile)) {
            oosUser.writeObject(users);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try (
                FileOutputStream workersRequestsFile = new FileOutputStream("worker_requests.txt", false);
                ObjectOutputStream oosWorkerRequest = new ObjectOutputStream(workersRequestsFile)) {
            oosWorkerRequest.writeObject(employerRequests);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        try (
                FileOutputStream employeeRequestsFile = new FileOutputStream("employee_requests.txt", false);
                ObjectOutputStream oosEmployeeRequest = new ObjectOutputStream(employeeRequestsFile)) {
            oosEmployeeRequest.writeObject(employeeRequests);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static void load() {
        try (ObjectInputStream oisUsers = new ObjectInputStream(new FileInputStream("users.txt"))) {
            users = (ArrayList<User>) oisUsers.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try (ObjectInputStream oisWorkerRequests = new ObjectInputStream(new FileInputStream("worker_requests.txt"))) {
            employerRequests = (ArrayList<Request>) oisWorkerRequests.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try (ObjectInputStream oisEmployeeRequests = new ObjectInputStream(new FileInputStream("employee_requests.txt"))) {
            employeeRequests = (ArrayList<Request>) oisEmployeeRequests.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void clearEmployerRequests(Client employee) {
        for (int i = employeeRequests.size()-1; i >= 0; i--) {
            if (employeeRequests.get(i).getRequester().getLogin().equals(employee.getLogin())) {
                employeeRequests.remove(employeeRequests.get(i));
            }
        }
    }

    public static String processRequests() {
        StringBuilder result = new StringBuilder();

        for (Request employerRequest : employerRequests) {
            for (int i = employeeRequests.size()-1; i >= 0; i--) {
                if (
                        employeeRequests.get(i).getHoursinWeek() == employerRequest.getHoursinWeek() &&
                                employeeRequests.get(i).getJob().equals(employerRequest.getJob()) &&
                                employeeRequests.get(i).getPayment() == employerRequest.getPayment()
                ) {
                    result.append(employeeRequests.get(i).getRequester().takeResult(employerRequest)).append("\n");
                    clearEmployerRequests(employeeRequests.get(i).getRequester());
                    break;
                }
            }
        }
        for (Request employeeRequest : employeeRequests) {
            result.append(employeeRequest.getRequester().getName()).append(", для вас работа не найдена\n");
        }
        System.out.println(result.toString());
        return result.toString();
    }
}
