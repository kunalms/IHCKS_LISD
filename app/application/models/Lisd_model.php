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

		function fetch_user_vehicles($data)
		{
			echo $data['user_id'];
			$this->db->from('user_vehicle')
			$this->db->where($data);
			$this->db->join('vehicle', 'user_vehicle.vehicle_id = vehicle.vehicle_id');
			$query=$this->db->get();
			return $query->result_array();

/*			$this->db->select('*');
			$this->db->from('blogs');
			$this->db->join('comments', 'comments.id = blogs.id');
			$query = $this->db->get();*/
		}

		function fetch_by_username($username)
		{
			$this->db->where($username);
			$this->db->limit(1);
			$query=$this->db->get('users');
			return $query->row();
		}

		function add_slider($data)
		{
			
			$this->db->insert('sliders',$data);

		}

		function disable_id($id)
		{
			$this->db->where('slider_id',$id);
			$data['status']=0;
			$this->db->update('sliders',$data);
		}

		function enable_id($id)
		{
			$this->db->where('slider_id',$id);
			$data['status']=1;
			$this->db->update('sliders',$data);
		}
		
	}

 ?>