package custq;

import java.time.LocalDate;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * JPA entity representing the company details
 * @author regen
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientCompany {

	@NotNull
	@Size(max=6, message="Username can be no more that 6 characters")
	private String userName;

	private Long id;

	@NotNull
	@Size(max=10, message="Company name can be no more that 10 characters")
    private String name;
	
	@NotNull
	private String description;
	
	private LocalDate createdDate;
	
	public ClientCompany() {
		this.createdDate = LocalDate.now();
	}

    public ClientCompany(final Long id, final String name, final String description, final String number) {
		this.id = id;
        this.name = name;
        this.description = description;
	}
	
	public ClientCompany(final String name, final String description, final String number) {
		this(null, name, description, number);
    }

    public Long getId() {
        return id;
    }

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

}
