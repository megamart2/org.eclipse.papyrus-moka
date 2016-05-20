/*
 * JSONClient.cpp
 *
 *  Created on: Mar 24, 2016
 *      Author: seb
 */

#include "JSONClient.h"


#include <iostream>
#include "rapidjson/writer.h"
#include "rapidjson/stringbuffer.h"





#ifdef _WIN32
#define FILE_SEPARATOR   "\\"
#define EXE_EXTENSION ".exe"
#else
#define FILE_SEPARATOR   "/"
#define EXE_EXTENSION " "
#endif


JSONClient::JSONClient():

	io_service(),
	socket(io_service) {

}

int JSONClient::init(const char * fmuResourceLocation){
		asio::ip::tcp::endpoint endpoint(asio::ip::tcp::v4(), 0);

		asio::ip::tcp::acceptor acceptor(io_service);
		//acceptor.set_option(asio::ip::tcp::acceptor::reuse_address(true));
		acceptor.open(endpoint.protocol());
		acceptor.bind(endpoint);
		acceptor.listen();
		asio::ip::tcp::endpoint le = acceptor.local_endpoint();

		unsigned short port = le.port();


		//asio::ip::tcp::endpoint endpoint(asio::ip::address::from_string("127.0.0.1"), portNumber);

		std::string locationString(fmuResourceLocation);

		locationString = replaceAll(locationString, "file://","");
		std::stringstream ss;
		ss << locationString <<FILE_SEPARATOR <<"rcp" << FILE_SEPARATOR<<"fmu_rcp" <<EXE_EXTENSION<<" -fmu "<< locationString << FILE_SEPARATOR << ".. -port " << port<< " &";



		std::cout << ss.str()<< std::endl;
		int status = std::system(ss.str().c_str());
		if (status == 0){
			acceptor.accept(socket);
		}

		return status;

}


std::string JSONClient::replaceAll(std::string str, const std::string& from, const std::string& to) {
    size_t start_pos = 0;
    while((start_pos = str.find(from, start_pos)) != std::string::npos) {
        str.replace(start_pos, from.length(), to);
        start_pos += to.length(); // Handles case where 'to' is a substring of 'from'
    }
    return str;
}

JSONClient::~JSONClient() {
	socket.close();

}





void JSONClient::sendRequest(rapidjson::Document& request, 	rapidjson::Document& response){

	// Convert JSON document to string
	rapidjson::StringBuffer reqbuf;
	rapidjson::Writer<rapidjson::StringBuffer> writer(reqbuf);
	request.Accept(writer);

	socket.write_some(asio::buffer(reqbuf.GetString(), reqbuf.GetSize()));

	/* This implementation is working, but is very slow!
	 * We should investigate why... Finally rather use a static buffer
	 */
//	boost::array<char,10> sizeBuff;
//	socket.read_some(asio::buffer(sizeBuff,10));


//    asio::read(socket, asio::buffer(sizeBuff,10),
//	                  asio::transfer_at_least(1));
//  socket.read_some(asio::buffer(sizeBuff));
//     int messageSize = atoi( sizeBuff.data() );


//	std::vector<char> messageBuff(messageSize);
//	boost::array<char,256> messageBuff;
//	socket.read_some(asio::buffer(messageBuff));
//	socket.read_some(asio::buffer(messageBuff,messageSize));

//	asio::read(socket, asio::buffer(messageBuff, messageSize),
//	                  asio::transfer_at_least(1));




	size_t nbBytes = socket.read_some(asio::buffer(hugeStaticBuff));

	response.Parse(&hugeStaticBuff[0], nbBytes);




}

