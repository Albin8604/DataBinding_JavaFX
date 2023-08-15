package ch.albin.ictskills.model;

import ch.albin.ictskills.model.viewModel.PersonView;
import ch.albin.ictskills.util.Converter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Person", schema = "DataBinding_Test")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPerson", nullable = false)
    private Integer id;

    @Column(name = "pNr", nullable = false)
    private Integer pNr;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "vorname", nullable = false, length = 45)
    private String vorname;

    @Column(name = "tel", length = 12)
    private String tel;

    @Column(name = "aktiv")
    private Byte aktiv;

    public Person() {
    }

    public Person(Integer id, Integer pNr, String name, String vorname, String tel, boolean aktiv) {
        this.id = id;
        this.pNr = pNr;
        this.name = name;
        this.vorname = vorname;
        this.tel = tel;
        setAktiv(aktiv);
    }

    public Person(PersonView personView){
        this(
                personView.getPerson().getId(),personView.getpNr(),
                personView.getName(), personView.getVorname(),
                personView.getTel(), personView.getAktiv()
        );
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPNr() {
        return pNr;
    }

    public void setPNr(Integer pNr) {
        this.pNr = pNr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public boolean getAktiv() {
        return Converter.convertByteToBoolean(aktiv == null ? 0 : aktiv);
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = Converter.convertBooleanToByte(aktiv);
    }

}