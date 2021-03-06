package org.apache.ode.bpel.obj;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.ode.bpel.obj.serde.OmSerdeFactory;
import org.apache.ode.bpel.obj.serde.OmSerdeFactory.SerializeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The class maintains all data that should be serialized. Including headers
 * like magic number, format etc. and the BPEL process. The header and whole
 * class could be extended.
 * 
 * An example:
 * {
 * "MAGIC":"VTVTAE9GSCAUBSk=",
 * "FORMAT":16,
 * "COMPILE_TIME":1401935206665,
 * "OTHER_HEADERS":{},
 * "PROCESS":{}
 * }
 * 
 * @author fangzhen
 */
public class OProcessWrapper extends ExtensibleImpl  implements Serializable{
	public static final long serialVersionUID = -1L;
	// constants
	public static final byte[] MAGIC_NUMBER_OFH_20140529 = new byte[] { 0x55,
			'5', 'S', 0x00, 'O', 'F', 'H', 0x20, 0x14, 0x05, 0x29 };
	public static final byte[] CURRENT_MAGIC_NUMBER = MAGIC_NUMBER_OFH_20140529;
	// key constants
	private static final String MAGIC_NUMBER = "magic";
	private static final String FORMAT = "format";
	private static final String COMPILE_TIME = "compileTime";
	private static final String GUID = "guid";
	private static final String PROCESS = "process";
	private static final String OTHER_HEADERS = "otherHeaders";
	private static final String TYPE = "type";

	public OProcessWrapper() {
		super(new LinkedHashMap<String, Object>());
		setMagic(OProcessWrapper.CURRENT_MAGIC_NUMBER);
		setCompileTime(0);
		setFormat(SerializeFormat.FORMAT_UNINITIALIZED);
		setOtherHeaders(new LinkedHashMap<String, Object>());
		
	}

	public OProcessWrapper(long compileTime) {
		this();
		setCompileTime(compileTime);
	}


	public void checkValid() throws OModelException {
		if (!Arrays.equals(getMagic(), MAGIC_NUMBER_OFH_20140529)){
			throw new OModelException("Unrecognized magic number");
		}
	}
	
	//Accessors
	@JsonIgnore
	public QName getType(){
		return (QName)fieldContainer.get(TYPE);
	}
	public void setType(QName qname){
		fieldContainer.put(TYPE, qname);
	}
	private void setType(String namespace, String local){
		fieldContainer.put(TYPE, new QName(namespace, local));
	}
	@JsonIgnore
	public byte[] getMagic() {
		return (byte[])fieldContainer.get(MAGIC_NUMBER);
	}

	public void setMagic(byte[] magic) {
		fieldContainer.put(MAGIC_NUMBER, magic);
	}

	@JsonIgnore
	public SerializeFormat getFormat() {
		return (SerializeFormat)fieldContainer.get(FORMAT);
	}

	public void setFormat(SerializeFormat format) {
		fieldContainer.put(FORMAT, format);
	}

	@JsonIgnore
	public long getCompileTime() {
		return (Long)fieldContainer.get(COMPILE_TIME);
	}

	public void setCompileTime(long compileTime) {
		fieldContainer.put(COMPILE_TIME, compileTime);
	}

	@JsonIgnore
	public String getGuid() {
		return (String)fieldContainer.get(GUID);
	}

	public void setGuid(String guid) {
		fieldContainer.put(GUID, guid);
	}

	@JsonIgnore
	@SuppressWarnings("unchecked")
	public Map<String, Object> getOtherHeaders() {
		Object o = fieldContainer.get(OTHER_HEADERS);
		return o == null ? null : (Map<String,Object>)o;
	}

	public void setOtherHeaders(Map<String, Object> otherHeaders) {
		fieldContainer.put(OTHER_HEADERS, otherHeaders);
	}

	@JsonIgnore
	public OProcess getProcess() {
		return (OProcess)fieldContainer.get(PROCESS);
	}
	public void setProcess(OProcess process) {
		setGuid(process.getGuid());
		setType(process.getTargetNamespace(), process.getProcessName());
		fieldContainer.put(OProcessWrapper.PROCESS, process);
	}
}
