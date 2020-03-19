package by.tms.school.model.comparator;

import by.tms.school.model.User;

import java.util.Comparator;

public class UserByPointsComparator implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        return o1.getPoints()-o2.getPoints();
    }
}
