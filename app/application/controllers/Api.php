<?php
defined('BASEPATH') OR exit('No direct script access allowed');

	/**
	* 
	*/
	class Api extends CI_Controller
	{
		
		function __construct()
		{
			parent::__construct();
			$this->load->model('lisd_model');
		}

		function index(){
			
			echo "api call";
		}

		function validate_user(){

			$username=$this->input->get('_USER_NAME');
			$password=$this->input->get('_PASSWORD');
			$data['user_name']=$username;
			$data['user_password']=$password;
			$res=$this->lisd_model->validate_user($data);

			if(sizeof($res)>0)
			{
			$ret['sessionId']=$res['user_id'];
			$ret['message']="login successful";
			echo json_encode($ret);
			}
			else{
			$ret['message']="login successful";
			echo json_encode($ret);	
			}

		}

		function fetch_user_vehicle(){
			$user=$this->input->get('_USER_NAME');
			$res=$this->lisd_model->fetch_user_vehicle($user);
			
		}

		function insert_user(){
			echo "api call input_user";
			$username=$this->input->get('username');
			echo $username;
		
			echo "api call input_user";
		}
	}

 ?>