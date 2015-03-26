package jumpers.com.pipervoice.Model;

/**
 * Created by Carla on 3/12/2015.
 */
public class Pessoa
{
    private String Nome;
    private int Id;
    private String Email;

    public void setNome(String nome)
    {
        Nome = nome;
    }

    public void setEmail(String email)
    {
        Email = email;
    }

    public void setId(int id)
    {
        Id = id;
    }

    public String getNome()
    {
        return Nome;
    }

    public int getId()
    {
        return Id;
    }

    public String getEmail()
    {
        return Email;
    }
}
