<?php 

	/**
	* 
	*/
	class Lisd_model extends CI_Model
	{
		
		function __construct()
		{
			parent::__construct();
		}

		function validate_user($data)
		{
			$this->db->where($data);
			$this->db->limit(1);
			$query=$this->db->get('users');
			return $query->result_array();
		}

		function insert_user($data)
		{
			$this->db->insert('users', $data);
			return true;
		}

		function insert_vehicle($data)
		{
			$this->db->insert('vehicle', $data);
			return true;
		}

		function fetch_user_vehicles($data)
		{
			$this->db->from('user_vehicle');
			$this->db->where($data);
			$this->db->join('vehicle', 'user_vehicle.vehicle_id = vehicle.vehicle_id');
			$query=$this->db->get();
			return $query->result_array();
		}




		function fetch_by_username($username)
		{
			$this->db->where($username);
			$this->db->limit(1);
			$query=$this->db->get('users');
			return $query->row();
		}

		function insert_gps_start($data){

			$this->db->insert('trip_start', $data);
			$insert_id = $this->db->insert_id();
			return  $insert_id;
		}

		function insert_gps_details($data){

			$this->db->insert('trip_details', $data);
			return true;
		}
		function fetch_flag_by_id($id){
			$this->db->where($id);
			$query=$this->db->get('vehicle');
			return $query->row();
		}

		function set_flag_by_id($id){
			$data=array('immobilize'=>"true");
			$this->db->where($id);
			$this->db->update('vehicle',$data);
			return true;
		}		
		function get_latlong($data){
			$this->db->where($data);
			$this->db->limit(1);
			$query=$this->db->get('trip_start');
			$ret=array();
			$row=$query->row();
			$ret['startlat']=$row->latitude;
			$ret['startlong']=$row->longitude;
			$data['is_trip_live']='f';

			//print_r($data);
			$this->db->where($data);
			$query1=$this->db->get('trip_details');
			$row1=$query1->row();
			$ret['endlat']=$row1->latitude;
			$ret['endlong']=$row1->longitude;
			return $ret;	
		}

		function fetch_gps_user($data)
		{
			$this->db->distinct();
			$this->db->select('trip_id');
			$this->db->where($data);

			$query=$this->db->get('trip_details');
			return $query->result_array();
		}

		function toggle_button($vehicle_id,$value){
			$info['immobilize']=$value;
			$this->db->where('vehicle_id',$vehicle_id);
			$this->db->update('vehicle',$info);
			return true;
		}

		function add_user_vehicle($data)
		{
			$this->db->where($data);
			$res=$this->db->get('user_vehicle');
			if($res->row()!=null){
				return true;
			}
			else{
			$this->db->insert('user_vehicle', $data);
			return true;
			}
		}

		function fetch_trip_by_trip_id($id){
			$this->db->where($id);
			$query=$this->db->get('trip_details');
			return $query->result_array();
		}

		function check_username($username){
			$this->db->where($username);
			$query=$this->db->get('users');
			if($res->row()!=null){
				return false;
			}
			else{
				return true;
			}
		}
	}

 ?>