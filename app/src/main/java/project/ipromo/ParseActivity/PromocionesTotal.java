package project.ipromo.ParseActivity;

import java.util.Date;

public class PromocionesTotal {
	private String title;
	private String subtitle;
	private String descripcion;
	private String image;
    private String uuid;
    private String precio;
    private Date fecha;
    private String imageCodigo;
    private String id;


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageCodigo() {
        return imageCodigo;
    }

    public void setImageCodigo(String imageCodigo) {
        this.imageCodigo = imageCodigo;
    }
}