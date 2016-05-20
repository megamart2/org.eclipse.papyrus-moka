/*
 * JSONClient.h
 *
 *  Created on: Mar 24, 2016
 *      Author: seb
 */

#ifndef JSON_INCLUDE_JSONCLIENT_H_
#define JSON_INCLUDE_JSONCLIENT_H_

#include "asio.hpp"
#include "rapidjson/rapidjson.h"
#include "rapidjson/document.h"
#include<boost/array.hpp>

#include <memory>

class JSONClient {

private  :




	asio::io_service io_service;
	asio::ip::tcp::socket socket;

	boost::array<char,1048576> hugeStaticBuff;
	std::string replaceAll(std::string str, const std::string& from, const std::string& to);

public:


	JSONClient();
	virtual ~JSONClient();

	int init(const char * fmuResourceLocation);
	void sendRequest(rapidjson::Document& request, 	rapidjson::Document& response);



};

#endif /* JSON_INCLUDE_JSONCLIENT_H_ */