package com.stephanHogenboom.acces;

import com.stephanHogenboom.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MasterClassDAO extends GeneralDAO {
    private Connection connection = getConnection();


    public List<Company> getAllCompanies() {
        String sql = "SELECT * FROM company;";
        List<Company> companies = new ArrayList();
        try (Statement stmnt = connection.createStatement();
             ResultSet rs = stmnt.executeQuery(sql)) {
            while (rs.next()) {
                Company company = new Company();
                company.setOid(rs.getInt(1));
                company.setName(rs.getString(2));
                companies.add(company);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return companies;
    }

    private Company getCompanyByOid(int oid) {
        String sql = String.format("SELECT * FROM company where oid = %s;", oid);
        try (Statement stmnt = connection.createStatement();
             ResultSet rs = stmnt.executeQuery(sql)) {
            if (rs.next()) {
                Company company = new Company();
                company.setOid(rs.getInt(1));
                company.setName(rs.getString(2));
                return company;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public boolean insertMasterClasser(MasterClasser mc) {
        boolean flag = false;
        String sql = "INSERT INTO master_classer VALUES (?, ?, ?, ?, ? ,? ,?, ?, ?, ?);";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            mc.setOid(incrementAndGetMaxId("master_classer"));
            stmt.setInt(1, mc.getOid());
            stmt.setString(2, mc.getFullName());
            stmt.setString(3, mc.getAddress().getKixCode());
            stmt.setString(4, mc.getStartDate().toString());
            stmt.setString(5, "");
            stmt.setInt(6, mc.getJobType().getOid());
            stmt.setString(7, mc.getTelephoneNumber());
            stmt.setString(8, mc.getEmail());
            stmt.setInt(9, mc.getCompany().getOid());
            stmt.setInt(10, mc.getFieldManager().getOid());

            insertAddress(mc.getAddress());
            addSpecializationsForMasterClasser(mc.getOid(), mc.getSpecializations());
            flag = stmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return flag;
    }


    public List<MasterClasser> getAllMasterClassers() {
        String sql = "SELECT * FROM master_classer;";
        List<MasterClasser> mcs = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                MasterClasser mc = new MasterClasser();
                mc.setOid(rs.getInt(1));
                mc.setFullName(rs.getString(2));
                mc.setAddress(getAddress(rs.getString(3)));
                mc.setStartDate(LocalDate.parse(rs.getString(4)));
                String endDate = rs.getString(5);
                mc.setEndDate(endDate.isEmpty() ? null : LocalDate.parse(endDate));
                mc.setJobType(getJobType(rs.getInt(6)));
                mc.setEmail(rs.getString(7));
                mc.setTelephoneNumber(rs.getString(8));
                mc.setCompany(getCompanyByOid(rs.getInt(9)));
                mc.setFieldManager(getFieldManager(rs.getInt(10)));
                mc.setSpecializations(getSpecilaizationsForMasterClasser(mc.getOid()));
                mcs.add(mc);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return mcs;
    }

    private JobType getJobType(int oid) {
        String sql = String.format("select * FROM job_type WHERE oid = %s;", oid);
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            if (rs.next()) {
                JobType jt = new JobType();
                jt.setOid(rs.getInt(1));
                jt.setName(rs.getString(2));
                return jt;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public List<JobType> getAllJobTypes() {
        String sql = "SELECT * FROM job_type";
        List<JobType> jobs = new ArrayList();
        try (Statement stmnt = connection.createStatement();
             ResultSet rs = stmnt.executeQuery(sql)) {
            while (rs.next()) {
                JobType jt = new JobType();
                jt.setOid(rs.getInt(1));
                jt.setName(rs.getString(2));
                jobs.add(jt);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jobs;
    }


    private Address getAddress(String kix) {
        Address address = new Address();
        String sql = String.format("SELECT * FROM address where kix_code = '%s';", kix);
        try (Statement stmnt = connection.createStatement();
             ResultSet rs = stmnt.executeQuery(sql)) {
            if (rs.next()) {
                address.setKixCode(kix);
                address.setCountry(rs.getString(2));
                address.setStreet(rs.getString(3));
                address.setHouseNumber(rs.getInt(4));
                address.setExtension(GeneralDAO.getStringSaveNullSave(rs.getString(5)));
                address.setPostalCode(rs.getString(6));
                address.setCity(rs.getString(7));
                return address;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void insertAddress(Address address) {
        String sql = "Insert into address VALUES (?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement stmnt = connection.prepareStatement(sql)) {
            stmnt.setString(1, address.getKixCode());
            stmnt.setString(2, address.getCountry());
            stmnt.setString(3, address.getStreet());
            stmnt.setInt(4, address.getHouseNumber());
            stmnt.setString(5, address.getExtension());
            stmnt.setString(6, address.getPostalCode());
            stmnt.setString(7, address.getCity());
            stmnt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private int incrementAndGetMaxId(String tableName) {
        String sql = String.format("SELECT max(oid) from %s;", tableName);
        int maxId = 0;
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            maxId = rs.getInt(1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return maxId + 1;
    }

    private FieldManager getFieldManager(int oid) {
        FieldManager fm = new FieldManager();
        String sql = String.format("SELECT * FROM field_manager where oid = '%s';", oid);
        try (Statement stmnt = connection.createStatement();
             ResultSet rs = stmnt.executeQuery(sql)) {
            if (rs.next()) {
                fm.setOid(oid);
                fm.setName(rs.getString(2));
                fm.setPhoneNumber(rs.getString(3));
                fm.setEmail(rs.getString(4));
                return fm;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<FieldManager> getAllFieldManagers() {
        List<FieldManager> fieldManagers = new ArrayList<>();
        String sql = "SELECT * FROM field_manager;";
        try (Statement stmnt = connection.createStatement();
             ResultSet rs = stmnt.executeQuery(sql)) {
            while (rs.next()) {
                FieldManager fm = new FieldManager();
                fm.setOid(rs.getInt(1));
                fm.setName(rs.getString(2));
                fm.setPhoneNumber(rs.getString(3));
                fm.setEmail(rs.getString(4));
                fieldManagers.add(fm);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return fieldManagers;
    }

    //TODO implement method
    public void insertCompany(String companyName) {

    }

    public void addSpecialization(String specialization) {
        //TODO add funcionality for this function
    }


    private void addSpecializationsForMasterClasser(int masterClasserId, List<Specialization> specializations) {
        specializations.forEach(System.out::println);
        specializations.forEach(specialization -> addSpecializationForMasterClasser(masterClasserId, specialization.getId()));
    }

    private void addSpecializationForMasterClasser(int masterClasserId, int specializationId) {
        String sql = "INSERT INTO specialization_masterclasser VALUES (? , ?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, masterClasserId);
            statement.setInt(2, specializationId);
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private List<Specialization> getSpecilaizationsForMasterClasser(int masterClasserId) {
        List<Specialization> specializations = new ArrayList<>();
        String sql = "SELECT sp.oid, sp.course_name FROM Specialization sp INNER JOIN specialization_masterclasser sm ON sp.oid =" +
                " sm.specialization_id WHERE sm.master_classer_id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, masterClasserId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Specialization specialization = new Specialization();
                    specialization.setId(rs.getInt(1));
                    specialization.setName(rs.getString(2));
                    specializations.add(specialization);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return specializations;
    }


    public List<Specialization> getAllSpecilaizations() {
        List<Specialization> specializations = new ArrayList<>();

        String sql = "SELECT * FROM Specialization;";
        try (Statement stmnt = connection.createStatement();
             ResultSet rs = stmnt.executeQuery(sql)) {
            while (rs.next()) {
                Specialization special = new Specialization();
                special.setId(rs.getInt(1));
                special.setName(rs.getString(2));
                specializations.add(special);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return specializations;
    }
}
