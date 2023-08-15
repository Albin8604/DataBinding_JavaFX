package ch.albin.ictskills.util.ui;

import ch.albin.ictskills.util.Validator;
import ch.albin.ictskills.util.ui.annotation.TableCol;
import ch.albin.ictskills.util.ui.annotation.TableIgnore;
import ch.albin.ictskills.util.ui.annotation.TableShow;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TableManager {
    /**
     * Will automatically set debug mode to false
     * <p>
     * how to use:
     * TableManager.initColumns(
     *                 data,table
     *         );
     *
     * @param data Date to be added to the Table
     * @param tableView Table
     * @param <T> for which dataclass this table should be initialized for
     */
    public static <T> void initColumns(ObservableList<T> data, TableView<T> tableView){
        initColumns(data,tableView,false);
    }

    /**
     * how to use:
     * TableManager.initColumns(
     *                 data,table,debug
     *         );
     *
     * @param data Date to be added to the Table
     * @param tableView Table
     * @param debug if in debug mode (just prints more details while initializing table)
     * @param <T> for which dataclass this table should be initialized for
     */
    public static <T> void initColumns(ObservableList<T> data, TableView<T> tableView,boolean debug) {
        if (Validator.isNullOrEmpty(data)) {
            throw new RuntimeException("Data not provided");
        }

        if (Validator.isNullOrEmpty(tableView.getColumns())) {
            throw new RuntimeException("Table has 0 columns");
        }

        List<Member> membersOfClazz = getMembersOfClazz(data.get(0).getClass());

        try {
            for (TableColumn<T, ?> column : tableView.getColumns()) {
                Member member = null;

                for (Member memberIteration : membersOfClazz) {
                    if (
                            memberIteration.getName().equalsIgnoreCase(column.getText()) ||
                                    memberIteration.getName().substring(3).equalsIgnoreCase(column.getText())
                    ){
                        member = memberIteration;
                        membersOfClazz.remove(memberIteration);
                        break;
                    }else if (memberIteration instanceof Method){
                        if (((Method)memberIteration).getAnnotation(TableCol.class).forCol().equalsIgnoreCase(column.getText())) {
                            member = memberIteration;
                        }
                    }
                }

                if (member == null && !getIgnoredMembersOfClazzByFieldName(data.get(0).getClass(), column.getText()).isEmpty()){
                    throw new RuntimeException("Field not found for TableCol "+column.getText());
                }

                final String memberName = member.getName();

                List<Member> showMethods = membersOfClazz.stream().filter(item -> {
                    if (item instanceof Method){
                        if (((Method) item).getAnnotation(TableShow.class) != null) {
                            if (((Method) item).getAnnotation(TableShow.class).forCol().equalsIgnoreCase(memberName)) {
                                return true;
                            }
                        }
                    }
                    return false;
                }).toList();

                /*
                if (!showMethods.isEmpty()){
                    column.setCellFactory(col -> {
                        TableCell<T, ?> cell = new TableCell<>();
                        cell.itemProperty().addListener((obs, old, newVal) -> {
                            if (newVal != null) {
                                Node centreBox = createPriorityGraphic(newVal);
                                cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(centreBox));
                            }
                        });
                        return cell;
                    });
                }else {

                }


                 */

                column.setCellValueFactory(new PropertyValueFactory<>(
                        getExtractedNameOfMember(member)
                ));

                if (debug){
                    System.out.println("--------------------");
                    System.out.print("Column: ");
                    System.out.println(column.getText());

                    System.out.print("Value Factory Value: ");
                    System.out.println(member.getName());
                    System.out.println("--------------------");
                }
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException(data.get(0).getClass().getName()+" less declared fields for TableCols");
        }

        tableView.setItems(data);
    }

    private static List<Member> getMembersOfClazz(Class<?> clazz){
        List<Member> membersOfClazz = new java.util.ArrayList<>(Arrays.stream(
                        clazz.getDeclaredMethods()
                ).filter(
                        method ->
                        //method.getAnnotation(TableCol.class) != null || method.getAnnotation(TableShow.class) != null
                        method.getAnnotation(TableCol.class) != null
                )
                .toList());

        membersOfClazz.addAll(new java.util.ArrayList<>(Arrays.stream(
                        clazz.getDeclaredFields()
                ).filter(field -> field.getAnnotation(TableIgnore.class) == null)
                .toList()));

        return membersOfClazz;
    }

    private static List<Member> getIgnoredMembersOfClazzByFieldName(Class<?> clazz,String fieldName){
        return new java.util.ArrayList<>(Arrays.stream(
                        clazz.getDeclaredFields()
                ).filter(
                        field ->
                                field.getAnnotation(TableIgnore.class) != null && field.getName().equalsIgnoreCase(fieldName)
                )
                .toList());
    }

    private static String getExtractedNameOfMember(Member member){
        if (member instanceof Method){
            return extractLowerCastMethodName(member);
        }else{
            return extractLowerCastName(member);
        }
    }
    private static String extractLowerCastName(Member member) {
        return (String.valueOf(member.getName().charAt(0))).toLowerCase() + member.getName().substring(1);
    }
    private static String extractLowerCastMethodName(Member member) {
        String name = member.getName().substring(3);
        return (String.valueOf(name.charAt(0))).toLowerCase() + name.substring(1);
    }
}
