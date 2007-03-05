/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.ode.dao.jpa;


import org.apache.ode.bpel.dao.MessageDAO;
import org.apache.ode.bpel.dao.MessageExchangeDAO;
import org.apache.ode.utils.DOMUtils;
import org.w3c.dom.Element;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.namespace.QName;


@Entity
@Table(name="ODE_MESSAGE")
public class MessageDAOImpl implements MessageDAO {

	@Id @Column(name="MESSAGE_ID") 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long _id;
	@Basic @Column(name="TYPE")
    private QName _type;
	@Lob @Column(name="DATA")
    private String _data;
	@Transient
    private Element _element;
	@ManyToOne(fetch=FetchType.LAZY,cascade={CascadeType.ALL}) @Column(name="MESSAGE_EXCHANGE_ID")
	private MessageExchangeDAOImpl _messageExchange;

	public MessageDAOImpl() {
		
	}
	public MessageDAOImpl(QName type, MessageExchangeDAOImpl me) {
		_type = type;
		_messageExchange = me;
	}
	
	public Element getData() {
		if ( _element == null && _data != null ) {
			try {
				_element = DOMUtils.stringToDOM(_data);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		return _element;
	}

	public MessageExchangeDAO getMessageExchange() {
		return _messageExchange;
	}

	public QName getType() {
		return _type;
	}

	public void setData(Element value) {
		_data = DOMUtils.domToString(value);
		_element = value;
	}

	public void setType(QName type) {
		_type = type;
	}

}
